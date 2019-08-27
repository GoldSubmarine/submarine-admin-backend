package org.javahub.submarine.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    XPage<Menu> findPage(XPage xPage, @Param("menu") Menu menu);

    List<Menu> findList(@Param("menu") Menu menu);

}
