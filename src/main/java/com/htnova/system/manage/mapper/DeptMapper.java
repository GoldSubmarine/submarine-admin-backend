package com.htnova.system.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.manage.dto.DeptDto;
import com.htnova.system.manage.entity.Dept;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeptMapper extends BaseMapper<Dept> {
    IPage<Dept> findPage(IPage<Void> xPage, @Param("deptDto") DeptDto deptDto);

    List<Dept> findList(@Param("deptDto") DeptDto deptDto);
}
