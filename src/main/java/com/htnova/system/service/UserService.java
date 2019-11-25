package com.htnova.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.common.base.BaseEntity;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.XPage;
import com.htnova.common.exception.ServiceException;
import com.htnova.common.util.CommonUtil;
import com.htnova.common.util.UserUtil;
import com.htnova.system.entity.*;
import com.htnova.system.mapper.UserMapper;
import com.htnova.system.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public XPage<User> findUserList(User user, XPage xPage) {
        XPage<User> userXPage = userMapper.findPage(xPage, user);
        return userXPage;
    }

    @Transactional(readOnly = true)
    public List<User> findUserList(User user) {
        return userMapper.findList(user);
    }

    @Transactional
    public String saveUser(User user) {
        Dept dept = deptService.getDeptById(user.getDeptId());
        user.setDeptIds(Optional.ofNullable(dept.getPids()).orElse("") + dept.getId() + ",");
        user.setDeptName(dept.getName());
        String randomPass = computePassword(user);
        super.saveOrUpdate(user);
        return randomPass;
    }

    private String computePassword(User user) {
        String randomPass = null;
        if(Objects.isNull(user.getId())) {
            //新建时创建jwt密钥和初始化密码
            user.setJwtSecret(CommonUtil.getRandomString(16));
            randomPass = CommonUtil.getRandomNum(6);
            user.setPassword(bCryptPasswordEncoder.encode(randomPass));
        } else {
            User source = userMapper.selectById(user.getId());
            user.setPassword(source.getPassword());
            user.setJwtSecret(source.getJwtSecret());
        }
        return randomPass;
    }

    @Transactional
    public void changePass(String oldPassword, String newPassword ) {
        User source = userMapper.selectById(UserUtil.getJwtUser().getId());
        if(!bCryptPasswordEncoder.matches(oldPassword, source.getPassword())) {
            throw new ServiceException(ResultStatus.OLD_PASSWORD_WRONG);
        }
        source.setPassword(bCryptPasswordEncoder.encode(newPassword));
        source.setJwtSecret(CommonUtil.getRandomString(16));
        super.saveOrUpdate(source);
    }

    @Transactional
    public String resetPass(Long id) {
        User source = userMapper.selectById(id);
        String pass = CommonUtil.getRandomNum(6);
        source.setPassword(bCryptPasswordEncoder.encode(pass));
        source.setJwtSecret(CommonUtil.getRandomString(16));
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
        User user = super.lambdaQuery().eq(User::getUsername, username).one();
        fillRolePermission(user);
        return user;
    }

    private void fillRolePermission(User user) {
        if(Objects.nonNull(user)) {
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
        userRoleService.remove(new LambdaQueryWrapper<>(new UserRole()).eq(UserRole::getUserId, id));
        super.removeById(id);
    }

    //===============冗余字段更新start===============

    @Async
    @TransactionalEventListener
    @Transactional
    public void updateOrgName(Dept.UpdateName UpdateName) {
        User user = new User();
        user.setDeptName(UpdateName.getName());
        super.update(user, new LambdaQueryWrapper<>(new User()).eq(User::getDeptId, user.getDeptId()));
        log.debug("Dept.UpdateName: {}", UpdateName);
    }

    //===============冗余字段更新end===============
}
