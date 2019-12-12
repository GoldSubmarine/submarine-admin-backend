package com.htnova.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.entity.Dictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictionaryMapper extends BaseMapper<Dictionary> {

    IPage<Dictionary> findPage(IPage<Void> xPage, @Param("dictionary") Dictionary dictionary);

    List<Dictionary> findList(@Param("dictionary") Dictionary dictionary);

}
