package com.htnova.scaffold.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.htnova.scaffold.common.dto.XPage;
import com.htnova.scaffold.modules.system.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    XPage<Menu> findPage(XPage xPage, @Param("menu") Menu menu);

    List<Menu> findList(@Param("menu") Menu menu);

}
