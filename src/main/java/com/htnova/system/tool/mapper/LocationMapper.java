package com.htnova.system.tool.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.tool.dto.LocationDto;
import com.htnova.system.tool.entity.Location;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LocationMapper extends BaseMapper<Location> {
    IPage<Location> findPage(IPage<Void> xPage, @Param("locationDto") LocationDto locationDto);

    List<Location> findList(@Param("locationDto") LocationDto locationDto);
}
