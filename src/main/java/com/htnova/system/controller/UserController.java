package com.htnova.system.controller;

import com.htnova.common.dto.XPage;
import com.htnova.common.util.CommonUtil;
import com.htnova.system.dto.UserDto;
import com.htnova.system.entity.User;
import com.htnova.system.mapstruct.UserMapStruct;
import com.htnova.system.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @menu 用户
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 分页查询
     */
    @GetMapping("/list/page")
    @PreAuthorize("hasAnyAuthority('user.find')")
    public XPage<UserDto> findListByPage(UserDto userDto, XPage xPage) {
        XPage<User> userPage = userService.findUserList(UserDto.toEntity(userDto), xPage);
        return userPage.toDto(UserMapStruct.class);
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
    public String save(UserDto userDto) {
        return userService.saveUser(UserDto.toEntity(userDto));
    }

    /**
     * 修改密码
     */
    @PostMapping("/changePass")
    public void changePass(String oldPassword, String newPassword) {
        userService.changePass(oldPassword, newPassword);
    }

    /**
     * 重置密码
     */
    @PostMapping("/resetPass")
    public String resetPass(Long id) {
        return userService.resetPass(id);
    }

    /**
     * 删除
     */
    @PreAuthorize("hasAnyAuthority('user.del')")
    @DeleteMapping("/del")
    public void delete(UserDto userDto) {
        userService.deleteUser(userDto.getId());
    }
}
