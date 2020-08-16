package com.htnova.system.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.manage.dto.DictionaryItemDto;
import com.htnova.system.manage.entity.DictionaryItem;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DictionaryItemMapper extends BaseMapper<DictionaryItem> {
    IPage<DictionaryItem> findPage(IPage<Void> xPage, @Param("dictionaryItemDto") DictionaryItemDto dictionaryItemDto);

    List<DictionaryItem> findList(@Param("dictionaryItemDto") DictionaryItemDto dictionaryItemDto);
}
