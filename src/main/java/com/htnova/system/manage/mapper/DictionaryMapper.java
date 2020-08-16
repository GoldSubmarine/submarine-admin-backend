package com.htnova.system.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.manage.dto.DictionaryDto;
import com.htnova.system.manage.entity.Dictionary;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DictionaryMapper extends BaseMapper<Dictionary> {
    IPage<Dictionary> findPage(IPage<Void> xPage, @Param("dictionaryDto") DictionaryDto dictionaryDto);

    List<Dictionary> findList(@Param("dictionaryDto") DictionaryDto dictionaryDto);
}
