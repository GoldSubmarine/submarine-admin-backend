package org.javahub.submarine.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.javahub.submarine.base.BaseEntity;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.common.util.CommonUtil;
import org.javahub.submarine.modules.system.entity.Dept;
import org.javahub.submarine.modules.system.entity.Role;
import org.javahub.submarine.modules.system.entity.User;
import org.javahub.submarine.modules.system.entity.UserRole;
import org.javahub.submarine.modules.system.mapper.UserMapper;
import org.javahub.submarine.modules.system.mapper.UserRoleMapper;
import org.springframework.scheduling.annotation.Async;
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
    private DeptService deptService;

    @Resource
    private UserRoleService userRoleService;

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
    public void saveUser(User user) {
        Dept dept = deptService.getDeptById(user.getDeptId());
        user.setDeptIds(dept.getPids() + dept.getId() + ",");
        user.setDeptName(dept.getName());
        if(Objects.isNull(user.getId())) {
            //新建时创建
            user.setJwtSecret(CommonUtil.getRandomString(16));
        }
        super.saveOrUpdate(user);
        // 保存角色
        List<Long> roleIdList = user.getRoleList().stream().map(BaseEntity::getId).collect(Collectors.toList());
        userRoleService.saveUserRole(user.getId(), roleIdList);
    }

    @Transactional
    public User getUserById(long id) {
        User user = userMapper.selectById(id);
        List<Role> roleList = userRoleMapper.getRoleById(id);
        user.setRoleList(roleList);
        return user;
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
