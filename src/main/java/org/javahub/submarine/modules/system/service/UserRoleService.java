package org.javahub.submarine.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.UserRole;
import org.javahub.submarine.modules.system.mapper.UserRoleMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames="UserRoleService")
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Transactional(readOnly = true)
    @Cacheable
    public XPage<UserRole> findUserRoleList(UserRole userRole, XPage xPage) {
        XPage<UserRole> userRoleXPage = userRoleMapper.findPage(xPage, userRole);
        return userRoleXPage;
    }

    @Transactional(readOnly = true)
    @Cacheable
    public List<UserRole> findUserRoleList(UserRole userRole) {
        return userRoleMapper.findList(userRole);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void saveUserRole(long userId, List<Long> roleIdList) {
        // 删除旧的
        super.remove(new LambdaQueryWrapper<>(new UserRole()).eq(UserRole::getUserId, userId));
        //保存新的
        List<UserRole> userRoleList = roleIdList.stream().map(item -> UserRole.builder().roleId(item).userId(userId).build()).collect(Collectors.toList());
        super.saveBatch(userRoleList);
    }

    @Transactional
    @Cacheable
    public UserRole getUserRoleById(long id) {
        return userRoleMapper.selectById(id);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void deleteUserRole(Long id) {
        super.removeById(id);
    }

}
