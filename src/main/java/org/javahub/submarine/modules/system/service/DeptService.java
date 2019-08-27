package org.javahub.submarine.modules.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javahub.submarine.base.BaseEntity;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.Dept;
import org.javahub.submarine.modules.system.mapper.DeptMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeptService extends ServiceImpl<DeptMapper, Dept> {

    @Resource
    private DeptMapper deptMapper;

    @Transactional(readOnly = true)
    public XPage<Dept> findDeptList(Dept dept, XPage xPage) {
        XPage<Dept> deptXPage = deptMapper.findPage(xPage, dept);
        return deptXPage;
    }

    @Transactional(readOnly = true)
    public List<Dept> findDeptList(Dept dept) {
        return deptMapper.findList(dept);
    }

    @Transactional
    public void saveDept(Dept dept) {
        Dept parent = super.getById(dept.getPid());
        dept.setPids(String.format("%s%s,", Optional.ofNullable(parent.getPids()).orElse(""), parent.getId()));
        super.saveOrUpdate(dept);
    }

    @Transactional
    public Dept getDeptById(Long id) {
        return deptMapper.selectById(id);
    }

    @Transactional
    public void deleteDept(Long id) {
        List<Dept> deptList = super.lambdaQuery().like(id != null, Dept::getPids, id).list();
        List<Long> ids = deptList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        ids.add(id);
        super.removeByIds(ids);
    }
}
