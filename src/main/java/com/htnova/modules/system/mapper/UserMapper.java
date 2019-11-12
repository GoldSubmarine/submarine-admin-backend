package com.htnova.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.htnova.common.dto.XPage;
import com.htnova.modules.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    XPage<User> findPage(XPage xPage, @Param("user") User user);

    List<User> findList(@Param("user") User user);

}
