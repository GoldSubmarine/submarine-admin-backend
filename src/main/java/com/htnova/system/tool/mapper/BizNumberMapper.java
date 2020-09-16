package com.htnova.system.tool.mapper;

import java.time.LocalDateTime;
import org.apache.ibatis.annotations.Param;

public interface BizNumberMapper {
    String getMaxBizNumber(@Param("table") String table, @Param("time") LocalDateTime time);
}
