package com.htnova.system.tool.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.tool.dto.QuartzJobDto;
import com.htnova.system.tool.entity.QuartzJob;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuartzJobMapper extends BaseMapper<QuartzJob> {
    IPage<QuartzJob> findPage(IPage<Void> xPage, @Param("quartzJobDto") QuartzJobDto quartzJobDto);

    List<QuartzJob> findList(@Param("quartzJobDto") QuartzJobDto quartzJobDto);
}
