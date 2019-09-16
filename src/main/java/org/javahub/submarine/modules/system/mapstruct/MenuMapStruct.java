package org.javahub.submarine.modules.system.mapstruct;

import org.javahub.submarine.common.base.BaseMapStruct;
import org.javahub.submarine.modules.system.dto.MenuDto;
import org.javahub.submarine.modules.system.entity.Menu;
import org.mapstruct.Mapper;

@Mapper
public interface MenuMapStruct extends BaseMapStruct<MenuDto, Menu> {

}
