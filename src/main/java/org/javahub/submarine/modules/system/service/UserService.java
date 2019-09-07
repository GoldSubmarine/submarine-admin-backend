package org.javahub.submarine.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javahub.submarine.base.BaseEntity;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.common.util.CommonUtil;
import org.javahub.submarine.modules.system.entity.*;
import org.javahub.submarine.modules.system.mapper.RoleMenuMapper;
import org.javahub.submarine.modules.system.mapper.RolePermissionMapper;
import org.javahub.submarine.modules.system.mapper.UserMapper;
import org.javahub.submarine.modules.system.mapper.UserRoleMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
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
    public String saveUser(User user) {
        String randomPass = null;
        Dept dept = deptService.getDeptById(user.getDeptId());
        user.setDeptIds(dept.getPids() + dept.getId() + ",");
        user.setDeptName(dept.getName());
        if(Objects.isNull(user.getId())) {
            //新建时创建
            user.setJwtSecret(CommonUtil.getRandomString(16));
            //初始化密码
            randomPass = CommonUtil.getRandomNum(6);
            user.setPassword(bCryptPasswordEncoder.encode(randomPass));
        }
        super.saveOrUpdate(user);
        // 保存角色
        List<Long> roleIdList = user.getRoleList().stream().map(BaseEntity::getId).collect(Collectors.toList());
        userRoleService.saveUserRole(user.getId(), roleIdList);
        return randomPass;
    }

    @Transactional
    public User getUserById(long id) {
        User user = userMapper.selectById(id);
        fillRolePermissionMenu(user);
        return user;
    }

    @Transactional
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
