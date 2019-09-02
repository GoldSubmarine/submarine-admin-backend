package org.javahub.submarine.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    XPage<User> findPage(XPage xPage, @Param("user") User user);

    List<User> findList(@Param("user") User user);

}
