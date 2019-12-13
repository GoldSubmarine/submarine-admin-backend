package com.htnova.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.dto.UserDto;
import com.htnova.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    IPage<User> findPage(IPage<Void> xPage, @Param("userDto") UserDto userDto);

    List<User> findList(@Param("userDto") UserDto userDto);

}
