package com.htnova.scaffold.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.htnova.scaffold.common.dto.XPage;
import com.htnova.scaffold.modules.system.entity.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    XPage<Permission> findPage(XPage xPage, @Param("permission") Permission permission);

    List<Permission> findList(@Param("permission") Permission permission);

}
