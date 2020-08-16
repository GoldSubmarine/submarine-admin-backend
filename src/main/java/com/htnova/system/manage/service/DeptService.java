package com.htnova.system.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.exception.ServiceException;
import com.htnova.system.manage.dto.DeptDto;
import com.htnova.system.manage.entity.Dept;
import com.htnova.system.manage.mapper.DeptMapper;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class DeptService extends ServiceImpl<DeptMapper, Dept> {
    @Resource
    private DeptMapper deptMapper;

    @Resource
    private ApplicationContext applicationContext;

    @Transactional(readOnly = true)
    public IPage<Dept> findDeptList(DeptDto deptDto, IPage<Void> xPage) {
        return deptMapper.findPage(xPage, deptDto);
    }

    @Transactional(readOnly = true)
    public List<Dept> findDeptList(DeptDto deptDto) {
        return deptMapper.findList(deptDto);
    }

    @Transactional
    public void saveDept(Dept dept) {
        Dept parent = super.getById(dept.getPid());
        checkCodeDuplicate(dept);
        if (parent != null) {
            dept.setPids(String.format("%s%s,", StringUtils.trimToEmpty(parent.getPids()), parent.getId()));
        }
        super.saveOrUpdate(dept);
        applicationContext.publishEvent(dept.saveEvent());
    }

    private void checkCodeDuplicate(Dept dept) {
        List<Dept> list = super.lambdaQuery().eq(Dept::getCode, dept.getCode()).list();
        if (!list.isEmpty() && !list.get(0).getId().equals(dept.getId())) {
            throw new ServiceException(ResultStatus.DEPT_CODE_DUPLICATE);
        }
    }

    @Transactional
    public Dept getDeptById(Long id) {
        return deptMapper.selectById(id);
    }

    @Transactional
    public void deleteDept(Long id) {
        if (Objects.nonNull(id)) {
            Dept dept = deptMapper.selectById(id);
            // 删除子级和自身
            List<Dept> deptList = super.lambdaQuery().like(Dept::getPids, id).list();
            if (!CollectionUtils.isEmpty(deptList)) {
                throw new ServiceException(ResultStatus.DEPT_HAS_CHILD);
            }
            super.removeById(id);
            applicationContext.publishEvent(dept.deleteEvent());
        }
    }
}
