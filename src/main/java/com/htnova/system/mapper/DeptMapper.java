package com.htnova.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.entity.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptMapper extends BaseMapper<Dept> {

    IPage<Dept> findPage(IPage<Void> xPage, @Param("dept") Dept dept);

    List<Dept> findList(@Param("dept") Dept dept);

}
