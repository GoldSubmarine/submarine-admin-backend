package com.htnova.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.htnova.common.dto.XPage;
import com.htnova.system.entity.DictionaryItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictionaryItemMapper extends BaseMapper<DictionaryItem> {

    XPage<DictionaryItem> findPage(XPage xPage, @Param("dictionaryItem") DictionaryItem dictionaryItem);

    List<DictionaryItem> findList(@Param("dictionaryItem") DictionaryItem dictionaryItem);

}
