package com.htnova.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.dto.DictionaryItemDto;
import com.htnova.system.entity.DictionaryItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictionaryItemMapper extends BaseMapper<DictionaryItem> {

    IPage<DictionaryItem> findPage(IPage<Void> xPage, @Param("dictionaryItemDto") DictionaryItemDto dictionaryItemDto);

    List<DictionaryItem> findList(@Param("dictionaryItemDto") DictionaryItemDto dictionaryItemDto);

}
