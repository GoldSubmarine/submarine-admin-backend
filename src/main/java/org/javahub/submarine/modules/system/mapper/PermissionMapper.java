package org.javahub.submarine.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    XPage<Permission> findPage(XPage xPage, @Param("permission") Permission permission);

    List<Permission> findList(@Param("permission") Permission permission);

}
