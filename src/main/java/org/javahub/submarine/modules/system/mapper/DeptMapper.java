package org.javahub.submarine.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.Dept;

import java.util.List;

public interface DeptMapper extends BaseMapper<Dept> {

    XPage<Dept> findPage(XPage xPage, @Param("dept") Dept dept);

    List<Dept> findList(@Param("dept") Dept dept);

}
