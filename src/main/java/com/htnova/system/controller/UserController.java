package com.htnova.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.common.util.CommonUtil;
import com.htnova.system.dto.UserDto;
import com.htnova.system.entity.User;
import com.htnova.system.mapstruct.UserMapStruct;
import com.htnova.system.service.UserRoleService;
import com.htnova.system.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @menu 用户
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    /**
     * 分页查询
     */
    @GetMapping("/list/page")
    @PreAuthorize("hasAnyAuthority('user.find')")
    public XPage<UserDto> findListByPage(UserDto userDto, XPage<Void> xPage) {
        IPage<User> userPage = userService.findUserList(UserDto.toEntity(userDto), xPage);
        return XPage.toDto(userPage, UserMapStruct.class);
    }

    /**
     * 查询
     */
    @PreAuthorize("hasAnyAuthority('user.find')")
    @GetMapping("/list/all")
    public List<UserDto> findList(UserDto userDto) {
        List<User> userList = userService.findUserList(UserDto.toEntity(userDto));
        return CommonUtil.toDto(userList, UserMapStruct.class);
    }

    /**
     * 详情
     */
    @PreAuthorize("hasAnyAuthority('user.find')")
    @GetMapping("/detail")
    public UserDto getById(long id) {
        User user = userService.getUserById(id);
        return UserDto.toDto(user);
    }

    /**
     * 保存
     */
    @PreAuthorize("hasAnyAuthority('user.add', 'user.edit')")
    @PostMapping("/save")
    public Result<String> save(@RequestBody UserDto userDto) {
        String randomPass = userService.saveUser(UserDto.toEntity(userDto));
        return Result.build(ResultStatus.SAVE_SUCCESS, randomPass);
    }

    /**
     * 角色绑定
     */
    @PreAuthorize("hasAnyAuthority('user.add', 'user.edit')")
    @PostMapping("/bind-role")
    public Result<Void> bingRole(@RequestBody UserDto userDto) {
        userRoleService.saveUserRole(userDto.getId(), userDto.getRoleIdList());
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /**
     * 修改密码
     */
    @PostMapping("/changePass")
    public Result<Void> changePass(@RequestBody Map<String, String> json) {
        userService.changePass(json.get("oldPassword"), json.get("newPassword"));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /**
     * 重置密码
     */
    @PostMapping("/resetPass")
    public Result<String> resetPass(@RequestBody UserDto userDto) {
        String pass = userService.resetPass(userDto.getId());
        return Result.build(ResultStatus.SAVE_SUCCESS, pass);
    }

    /**
     * 删除
     */
    @PreAuthorize("hasAnyAuthority('user.del')")
    @DeleteMapping("/del")
    public Result<Void> delete(@RequestBody UserDto userDto) {
        userService.deleteUser(userDto.getId());
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
