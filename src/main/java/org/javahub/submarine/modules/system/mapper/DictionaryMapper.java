package org.javahub.submarine.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.Dictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictionaryMapper extends BaseMapper<Dictionary> {

    XPage<Dictionary> findPage(XPage xPage, @Param("dictionary") Dictionary dictionary);

    List<Dictionary> findList(@Param("dictionary") Dictionary dictionary);

}
