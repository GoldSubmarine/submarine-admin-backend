package com.htnova.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    IPage<Role> findPage(IPage<Void> xPage, @Param("role") Role role);

    List<Role> findList(@Param("role") Role role);

}
