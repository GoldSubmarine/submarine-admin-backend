package com.htnova.modules.system.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import org.apache.commons.lang3.StringUtils;
import com.htnova.modules.system.dto.UserDto;
import com.htnova.modules.system.entity.Role;
import com.htnova.modules.system.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper
public interface UserMapStruct extends BaseMapStruct<UserDto, User> {

    @AfterMapping // or @BeforeMapping
    default void toEntityRoleIdList(User user, @MappingTarget UserDto userDto) {
        if(!CollectionUtils.isEmpty(user.getRoleList())) {
            String roleIdList = user.getRoleList().stream().map(item -> String.valueOf(item.getId())).collect(Collectors.joining(","));
            userDto.setRoleIdList(roleIdList);
        }
    }

    @AfterMapping // or @BeforeMapping
    default void toDtoRoleIdList(UserDto userDto, @MappingTarget User user) {
        if(StringUtils.isNotEmpty(userDto.getRoleIdList())) {
            List<Role> roleList = Stream.of(userDto.getRoleIdList().split(",")).map(item -> new Role() {{
                setId(Long.valueOf(item));
            }}).collect(Collectors.toList());
            user.setRoleList(roleList);
        }
    }
}