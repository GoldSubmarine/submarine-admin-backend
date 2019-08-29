package org.javahub.submarine.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.Menu;
import org.javahub.submarine.modules.system.entity.RoleMenu;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    XPage<RoleMenu> findPage(XPage xPage, @Param("roleMenu") RoleMenu roleMenu);

    List<RoleMenu> findList(@Param("roleMenu") RoleMenu roleMenu);

    List<Menu> getByRoleId(long id);

}
