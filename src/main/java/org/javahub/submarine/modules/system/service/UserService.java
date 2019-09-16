package org.javahub.submarine.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javahub.submarine.common.base.BaseEntity;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.common.exception.ServiceException;
import org.javahub.submarine.common.util.CommonUtil;
import org.javahub.submarine.common.util.UserUtil;
import org.javahub.submarine.modules.system.entity.*;
import org.javahub.submarine.modules.system.mapper.RoleMenuMapper;
import org.javahub.submarine.modules.system.mapper.RolePermissionMapper;
import org.javahub.submarine.modules.system.mapper.UserMapper;
import org.javahub.submarine.modules.system.mapper.UserRoleMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames="UserService")
public class UserService extends ServiceImpl<UserMapper, User> {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

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
    @CacheEvict(allEntries = true)
    public String saveUser(User user) {
        String randomPass = null;
        Dept dept = deptService.getDeptById(user.getDeptId());
        user.setDeptIds(Optional.ofNullable(dept.getPids()).orElse("") + dept.getId() + ",");
        user.setDeptName(dept.getName());
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
        super.saveOrUpdate(user);
        // 保存角色
        List<Long> roleIdList = user.getRoleList().stream().map(BaseEntity::getId).collect(Collectors.toList());
        userRoleService.saveUserRole(user.getId(), roleIdList);
        return randomPass;
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void changePass(String oldPassword, String newPassword ) {
        User source = userMapper.selectById(UserUtil.getJwtUser().getId());
        if(!bCryptPasswordEncoder.matches(oldPassword, source.getPassword())) {
            throw new ServiceException("原密码错误");
        }
        source.setPassword(bCryptPasswordEncoder.encode(newPassword));
        source.setJwtSecret(CommonUtil.getRandomString(16));
        super.saveOrUpdate(source);
    }

    @Transactional
    @CacheEvict(allEntries = true)
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
        fillRolePermissionMenu(user);
        return user;
    }

    @Transactional
    @Cacheable
    public User getByUsername(String username) {
        User user = super.lambdaQuery().eq(User::getUsername, username).one();
        fillRolePermissionMenu(user);
        return user;
    }

    private void fillRolePermissionMenu(User user) {
        if(Objects.nonNull(user)) {
            List<Role> roleList = userRoleMapper.getRoleById(user.getId());
            List<Long> roleIds = roleList.stream().map(BaseEntity::getId).collect(Collectors.toList());
            List<Permission> permissionList = rolePermissionMapper.getByRoleIds(roleIds)
                                                .stream().filter(item -> StringUtils.isNotBlank(item.getValue())).collect(Collectors.toList());
            List<Menu> menuList = roleMenuMapper.getByRoleIds(roleIds);
            user.setRoleList(roleList);
            user.setPermissionList(permissionList);
            user.setMenuList(menuList);
        }
    }


    @Transactional
    @CacheEvict(allEntries = true)
    public void deleteUser(Long id) {
        // 删除用户角色关联表
        userRoleService.remove(new LambdaQueryWrapper<>(new UserRole()).eq(UserRole::getUserId, id));
        super.removeById(id);
    }

    //===============冗余字段更新start===============

    @Async
    @TransactionalEventListener
    @Transactional
    @CacheEvict(allEntries = true)
    public void updateOrgName(Dept.UpdateName UpdateName) {
        User user = new User();
        user.setDeptName(UpdateName.getName());
        super.update(user, new LambdaQueryWrapper<>(new User()).eq(User::getDeptId, user.getDeptId()));
        log.debug("Dept.UpdateName: {}", UpdateName);
    }

    //===============冗余字段更新end===============
}
