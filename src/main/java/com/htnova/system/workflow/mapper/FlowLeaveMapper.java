package com.htnova.system.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.workflow.dto.FlowLeaveDto;
import com.htnova.system.workflow.entity.FlowLeave;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FlowLeaveMapper extends BaseMapper<FlowLeave> {

    IPage<FlowLeave> findPage(IPage<Void> xPage, @Param("flowLeaveDto") FlowLeaveDto flowLeaveDto);

    List<FlowLeave> findList(@Param("flowLeaveDto") FlowLeaveDto flowLeaveDto);
}
