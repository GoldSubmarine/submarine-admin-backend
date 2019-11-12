package com.htnova.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.htnova.common.dto.XPage;
import org.apache.ibatis.annotations.Param;
import com.htnova.system.entity.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    XPage<Permission> findPage(XPage xPage, @Param("permission") Permission permission);

    List<Permission> findList(@Param("permission") Permission permission);

}
