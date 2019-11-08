package com.htnova.scaffold.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.htnova.scaffold.common.dto.XPage;
import com.htnova.scaffold.modules.system.entity.Menu;
import com.htnova.scaffold.modules.system.entity.RoleMenu;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    XPage<RoleMenu> findPage(XPage xPage, @Param("roleMenu") RoleMenu roleMenu);

    List<RoleMenu> findList(@Param("roleMenu") RoleMenu roleMenu);

    List<Menu> getByRoleId(long id);

    List<Menu> getByRoleIds(@Param("ids") List<Long> ids);

}
