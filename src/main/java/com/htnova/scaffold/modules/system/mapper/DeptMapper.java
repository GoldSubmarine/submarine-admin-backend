package com.htnova.scaffold.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.htnova.scaffold.common.dto.XPage;
import com.htnova.scaffold.modules.system.entity.Dept;

import java.util.List;

public interface DeptMapper extends BaseMapper<Dept> {

    XPage<Dept> findPage(XPage xPage, @Param("dept") Dept dept);

    List<Dept> findList(@Param("dept") Dept dept);

}
