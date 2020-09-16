package com.htnova.system.manage.controller;

import com.htnova.common.constant.ResultStatus;
import com.htnova.common.converter.DtoConverter;
import com.htnova.common.dto.Result;
import com.htnova.common.util.UserUtil;
import com.htnova.system.manage.dto.UserDto;
import com.htnova.system.manage.entity.User;
import com.htnova.system.manage.mapstruct.UserMapStruct;
import com.htnova.system.manage.service.UserService;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** @menu 用户中心接口 */
@RestController
@RequestMapping("/user-center")
public class UserCenterController {
    @Resource
    private UserService userService;

    /** 详情 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail")
    public UserDto userDetail() {
        User user = userService.getUserById(UserUtil.getAuthUser().getId());
        return DtoConverter.toDto(user, UserMapStruct.INSTANCE);
    }

    /** 保存 */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/save")
    public Result<String> save(@RequestBody UserDto userDto) {
        userDto.setId(UserUtil.getAuthUser().getId());
        String randomPass = userService.saveUser(DtoConverter.toEntity(userDto, UserMapStruct.INSTANCE));
        return Result.build(ResultStatus.SAVE_SUCCESS, randomPass);
    }

    /** 修改密码 */
    @PostMapping("/changePass")
    @PreAuthorize("isAuthenticated()")
    public Result<Void> changePass(@RequestBody Map<String, String> json) {
        userService.changePass(json.get("oldPassword"), json.get("newPassword"));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }
}
