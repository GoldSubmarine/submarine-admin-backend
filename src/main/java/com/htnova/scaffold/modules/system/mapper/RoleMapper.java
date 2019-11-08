package com.htnova.scaffold.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.htnova.scaffold.common.dto.XPage;
import com.htnova.scaffold.modules.system.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    XPage<Role> findPage(XPage xPage, @Param("role") Role role);

    List<Role> findList(@Param("role") Role role);

}
