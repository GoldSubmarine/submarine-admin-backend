package com.htnova.system.manage.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.manage.dto.UserDto;
import com.htnova.system.manage.entity.Role;
import com.htnova.system.manage.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

@Mapper
public interface UserMapStruct extends BaseMapStruct<UserDto, User> {
    UserMapStruct INSTANCE = Mappers.getMapper(UserMapStruct.class);

    @AfterMapping // or @BeforeMapping
    default void toEntityRoleIdList(User user, @MappingTarget UserDto userDto) {
        if (!CollectionUtils.isEmpty(user.getRoleList())) {
            String roleIdList = user
                .getRoleList()
                .stream()
                .map(item -> String.valueOf(item.getId()))
                .collect(Collectors.joining(","));
            userDto.setRoleIds(roleIdList);
        }
    }

    @AfterMapping // or @BeforeMapping
    default void toDtoRoleIdList(UserDto userDto, @MappingTarget User user) {
        if (StringUtils.isNotEmpty(userDto.getRoleIds())) {
            List<Role> roleList = Stream
                .of(userDto.getRoleIds().split(","))
                .map(
                    item ->
                        new Role() {

                            {
                                setId(Long.valueOf(item));
                            }
                        }
                )
                .collect(Collectors.toList());
            user.setRoleList(roleList);
        }
    }
}
