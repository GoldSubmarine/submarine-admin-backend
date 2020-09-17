package com.htnova.system.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.common.base.BaseEntity;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.exception.ServiceException;
import com.htnova.common.util.CommonUtil;
import com.htnova.common.util.UserUtil;
import com.htnova.security.entity.AuthUser;
import com.htnova.security.mapstruct.AuthUserMapStruct;
import com.htnova.system.manage.dto.UserDto;
import com.htnova.system.manage.entity.*;
import com.htnova.system.manage.mapper.UserMapper;
import com.htnova.system.manage.mapper.UserRoleMapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private DeptService deptService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private PermissionService permissionService;

    @Transactional(readOnly = true)
    public IPage<User> findUserList(UserDto userDto, IPage<Void> xPage) {
        return userMapper.findPage(xPage, userDto);
    }

    @Transactional(readOnly = true)
    public List<User> findUserList(UserDto userDto) {
        return userMapper.findList(userDto);
    }

    @Transactional(readOnly = true)
    public AuthUser getAuthUser(User user) {
        return AuthUserMapStruct.INSTANCE.toAuthUser(user);
    }

    @Transactional
    public String saveUser(User user) {
        // 编辑
        checkUserNameDuplicate(user);
        Dept dept = deptService.getDeptById(user.getDeptId());
        user.setDeptIds(Optional.ofNullable(dept.getPids()).orElse("") + dept.getId() + ",");
        user.setDeptName(dept.getName());
        String randomPass = computePassword(user);
        super.saveOrUpdate(user);
        applicationContext.publishEvent(user.saveEvent());
        return randomPass;
    }

    private void checkUserNameDuplicate(User user) {
        List<User> list = super.lambdaQuery().eq(User::getUsername, user.getUsername()).list();
        if (!list.isEmpty() && !list.get(0).getId().equals(user.getId())) {
            throw new ServiceException(ResultStatus.USERNAME_DUPLICATE);
        }
    }

    private String computePassword(User user) {
        String randomPass = null;
        if (Objects.isNull(user.getId())) {
            // 新建时创建初始化密码
            randomPass = CommonUtil.getRandomNum(6);
            user.setPassword(bCryptPasswordEncoder.encode(randomPass));
        } else {
            User source = userMapper.selectById(user.getId());
            user.setPassword(source.getPassword());
        }
        return randomPass;
    }

    @Transactional
    public void changePass(String oldPassword, String newPassword) {
        User source = userMapper.selectById(UserUtil.getAuthUser().getId());
        if (!bCryptPasswordEncoder.matches(oldPassword, source.getPassword())) {
            throw new ServiceException(ResultStatus.OLD_PASSWORD_WRONG);
        }
        source.setPassword(bCryptPasswordEncoder.encode(newPassword));
        source.setToken(CommonUtil.getRandomString(16));
        super.saveOrUpdate(source);
    }

    @Transactional
    public String resetPass(Long id) {
        User source = userMapper.selectById(id);
        String pass = CommonUtil.getRandomNum(6);
        source.setPassword(bCryptPasswordEncoder.encode(pass));
        source.setToken(CommonUtil.getRandomString(16));
        super.saveOrUpdate(source);
        return pass;
    }

    @Transactional
    public User getUserById(long id) {
        User user = userMapper.selectById(id);
        fillRolePermission(user);
        return user;
    }

    @Transactional
    public User getByUsername(String username) {
        User user = super
            .lambdaQuery()
            .eq(User::getUsername, username)
            .eq(User::getStatus, User.UserStatus.enable)
            .one();
        fillRolePermission(user);
        return user;
    }

    @Transactional
    public User getByToken(String token) {
        User user = super.lambdaQuery().eq(User::getToken, token).one();
        fillRolePermission(user);
        return user;
    }

    private void fillRolePermission(User user) {
        if (Objects.nonNull(user)) {
            List<Role> roleList = userRoleMapper.getRoleByUserId(user.getId());
            List<Permission> permissionList = rolePermissionService.findPermissionList(
                roleList.stream().map(BaseEntity::getId).collect(Collectors.toList())
            );
            user.setRoleList(roleList);
            user.setPermissionList(permissionList);
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        // 删除用户角色关联表
        User user = super.getById(id);
        userRoleService.remove(new LambdaQueryWrapper<>(new UserRole()).eq(UserRole::getUserId, id));
        super.removeById(id);
        applicationContext.publishEvent(user.deleteEvent());
    }

    @Async
    @TransactionalEventListener
    @Transactional
    public void deptSaveEventHandle(Dept.SaveEvent saveEvent) {
        Dept dept = saveEvent.getDept();
        super.lambdaUpdate().eq(User::getDeptId, dept.getId()).set(User::getDeptName, dept.getName()).update();
        log.info("User reply {}", saveEvent);
    }

    @EventListener
    @Transactional
    public void deptDeleteEventHandle(Dept.DeleteEvent deleteEvent) {
        Dept dept = deleteEvent.getDept();
        if (Objects.nonNull(dept.getId())) {
            List<User> list = super.lambdaQuery().eq(User::getDeptId, dept.getId()).list();
            if (!list.isEmpty()) {
                throw new ServiceException(ResultStatus.DEPT_HAS_USER, list);
            }
        }
        log.info("User reply {}", deleteEvent);
    }
}
