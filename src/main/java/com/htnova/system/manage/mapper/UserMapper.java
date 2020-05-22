package com.htnova.system.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.manage.dto.UserDto;
import com.htnova.system.manage.entity.User;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {
    IPage<User> findPage(IPage<Void> xPage, @Param("userDto") UserDto userDto);

    List<User> findList(@Param("userDto") UserDto userDto);
}
