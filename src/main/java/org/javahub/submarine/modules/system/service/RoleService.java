package org.javahub.submarine.modules.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.Role;
import org.javahub.submarine.modules.system.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Resource
    private RoleMapper roleMapper;

    @Transactional(readOnly = true)
    public XPage<Role> findRoleList(Role role, XPage xPage) {
        XPage<Role> roleXPage = roleMapper.findPage(xPage, role);
        return roleXPage;
    }

    @Transactional(readOnly = true)
    public List<Role> findRoleList(Role role) {
        return roleMapper.findList(role);
    }

    @Transactional
    public void saveRole(Role role) {
        super.saveOrUpdate(role);
    }

    @Transactional
    public Role getRoleById(Long id) {
        return roleMapper.selectById(id);
    }

    @Transactional
    public void deleteRole(Long id) {
        super.removeById(id);
    }
}
