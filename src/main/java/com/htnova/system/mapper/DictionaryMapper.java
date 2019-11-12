package com.htnova.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.htnova.common.dto.XPage;
import com.htnova.system.entity.Dictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictionaryMapper extends BaseMapper<Dictionary> {

    XPage<Dictionary> findPage(XPage xPage, @Param("dictionary") Dictionary dictionary);

    List<Dictionary> findList(@Param("dictionary") Dictionary dictionary);

}
