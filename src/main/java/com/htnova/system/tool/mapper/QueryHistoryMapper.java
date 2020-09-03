package com.htnova.system.tool.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QueryHistoryMapper {
    List<String> queryHistory(@Param("fieldName") String fieldName, @Param("tableName") String tableName);

    List<String> queryHistoryLike(
        @Param("fieldName") String fieldName,
        @Param("tableName") String tableName,
        @Param("value") String value
    );
}
