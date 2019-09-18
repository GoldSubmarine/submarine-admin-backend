package org.javahub.submarine.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.DictionaryItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictionaryItemMapper extends BaseMapper<DictionaryItem> {

    XPage<DictionaryItem> findPage(XPage xPage, @Param("dictionaryItem") DictionaryItem dictionaryItem);

    List<DictionaryItem> findList(@Param("dictionaryItem") DictionaryItem dictionaryItem);

}
