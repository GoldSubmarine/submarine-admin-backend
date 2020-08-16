package com.htnova.system.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.system.manage.entity.UserRole;
import com.htnova.system.manage.mapper.UserRoleMapper;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {
    @Resource
    private UserRoleMapper userRoleMapper;

    @Transactional(readOnly = true)
    public IPage<UserRole> findUserRoleList(UserRole userRole, IPage<Void> xPage) {
        return userRoleMapper.findPage(xPage, userRole);
    }

    @Transactional(readOnly = true)
    public List<UserRole> findUserRoleList(UserRole userRole) {
        return userRoleMapper.findList(userRole);
    }

    @Transactional
    public void saveUserRole(long userId, List<Long> roleIdList) {
        // 删除旧的
        super.remove(new LambdaQueryWrapper<>(new UserRole()).eq(UserRole::getUserId, userId));
        // 保存新的
        List<UserRole> userRoleList = roleIdList
            .stream()
            .map(item -> UserRole.builder().roleId(item).userId(userId).build())
            .collect(Collectors.toList());
        super.saveBatch(userRoleList);
    }

    @Transactional
    public UserRole getUserRoleById(long id) {
        return userRoleMapper.selectById(id);
    }

    @Transactional
    public void deleteUserRole(Long id) {
        super.removeById(id);
    }
}
