package com.htnova.system.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.converter.DtoConverter;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.system.manage.dto.UserDto;
import com.htnova.system.manage.entity.User;
import com.htnova.system.manage.mapstruct.UserMapStruct;
import com.htnova.system.manage.service.UserRoleService;
import com.htnova.system.manage.service.UserService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/** @menu 用户 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    /** 分页查询 */
    @PreAuthorize("hasAnyAuthority('user.find')")
    @GetMapping("/list/page")
    public XPage<UserDto> findListByPage(UserDto userDto, XPage<Void> xPage) {
        IPage<User> userPage = userService.findUserList(userDto, XPage.toIPage(xPage));
        return DtoConverter.toDto(userPage, UserMapStruct.INSTANCE);
    }

    /** 查询 */
    @PreAuthorize("hasAnyAuthority('user.find')")
    @GetMapping("/list/all")
    public List<UserDto> findList(UserDto userDto) {
        List<User> userList = userService.findUserList(userDto);
        return DtoConverter.toDto(userList, UserMapStruct.INSTANCE);
    }

    /** 详情 */
    @PreAuthorize("hasAnyAuthority('user.find')")
    @GetMapping("/detail/{id}")
    public UserDto getById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return DtoConverter.toDto(user, UserMapStruct.INSTANCE);
    }

    /** 保存 */
    @PreAuthorize("hasAnyAuthority('user.add', 'user.edit')")
    @PostMapping("/save")
    public Result<String> save(@RequestBody UserDto userDto) {
        String randomPass = userService.saveUser(DtoConverter.toEntity(userDto, UserMapStruct.INSTANCE));
        return Result.build(ResultStatus.SAVE_SUCCESS, randomPass);
    }

    /** 角色绑定 */
    @PreAuthorize("hasAnyAuthority('user.add', 'user.edit')")
    @PostMapping("/bind-role")
    public Result<Void> bingRole(@RequestBody UserDto userDto) {
        userRoleService.saveUserRole(userDto.getId(), userDto.getRoleIdList());
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 修改密码 */
    @PreAuthorize("hasAnyAuthority('user.add', 'user.edit')")
    @PostMapping("/changePass")
    public Result<Void> changePass(@RequestBody Map<String, String> json) {
        userService.changePass(json.get("oldPassword"), json.get("newPassword"));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 重置密码 */
    @PreAuthorize("hasAnyAuthority('user.add', 'user.edit')")
    @PostMapping("/resetPass")
    public Result<String> resetPass(@RequestBody UserDto userDto) {
        String pass = userService.resetPass(userDto.getId());
        return Result.build(ResultStatus.SAVE_SUCCESS, pass);
    }

    /** 删除 */
    @PreAuthorize("hasAnyAuthority('user.del')")
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable long id) {
        userService.deleteUser(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
