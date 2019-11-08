package com.htnova.scaffold.modules.system.mapstruct;

import com.htnova.scaffold.common.base.BaseMapStruct;
import com.htnova.scaffold.modules.system.dto.MenuDto;
import com.htnova.scaffold.modules.system.entity.Menu;
import org.mapstruct.Mapper;

@Mapper
public interface MenuMapStruct extends BaseMapStruct<MenuDto, Menu> {

}
