package com.htnova.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.htnova.common.dto.XPage;
import org.apache.ibatis.annotations.Param;
import com.htnova.system.entity.Dept;

import java.util.List;

public interface DeptMapper extends BaseMapper<Dept> {

    XPage<Dept> findPage(XPage xPage, @Param("dept") Dept dept);

    List<Dept> findList(@Param("dept") Dept dept);

}
