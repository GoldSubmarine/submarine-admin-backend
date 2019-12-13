package com.htnova.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.dto.DeptDto;
import com.htnova.system.entity.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptMapper extends BaseMapper<Dept> {

    IPage<Dept> findPage(IPage<Void> xPage, @Param("deptDto") DeptDto deptDto);

    List<Dept> findList(@Param("deptDto") DeptDto deptDto);

}
