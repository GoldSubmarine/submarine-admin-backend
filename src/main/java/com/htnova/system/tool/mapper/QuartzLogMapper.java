package com.htnova.system.tool.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.tool.dto.QuartzLogDto;
import com.htnova.system.tool.entity.QuartzLog;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuartzLogMapper extends BaseMapper<QuartzLog> {
    IPage<QuartzLog> findPage(IPage<Void> xPage, @Param("quartzLogDto") QuartzLogDto quartzLogDto);

    List<QuartzLog> findList(@Param("quartzLogDto") QuartzLogDto quartzLogDto);
}
