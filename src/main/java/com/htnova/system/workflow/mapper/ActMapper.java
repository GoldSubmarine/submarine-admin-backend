package com.htnova.system.workflow.mapper;

import org.apache.ibatis.annotations.Param;

public interface ActMapper {
    int updateProcInsIdByBusinessId(
        @Param("businessTable") String businessTable,
        @Param("processInstanceId") String processInstanceId,
        @Param("businessId") String businessId
    );
}
