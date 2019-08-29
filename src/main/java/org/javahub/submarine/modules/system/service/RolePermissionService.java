package org.javahub.submarine.modules.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.RolePermission;
import org.javahub.submarine.modules.system.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermission> {

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Transactional(readOnly = true)
    public XPage<RolePermission> findRolePermissionList(RolePermission rolePermission, XPage xPage) {
        XPage<RolePermission> rolePermissionXPage = rolePermissionMapper.findPage(xPage, rolePermission);
        return rolePermissionXPage;
    }

    @Transactional(readOnly = true)
    public List<RolePermission> findRolePermissionList(RolePermission rolePermission) {
        return rolePermissionMapper.findList(rolePermission);
    }

    @Transactional
    public void saveRolePermission(RolePermission rolePermission) {
        super.saveOrUpdate(rolePermission);
    }

    @Transactional
    public RolePermission getRolePermissionById(Long id) {
        return rolePermissionMapper.selectById(id);
    }

    @Transactional
    public void deleteRolePermission(Long id) {
        super.removeById(id);
    }
}
