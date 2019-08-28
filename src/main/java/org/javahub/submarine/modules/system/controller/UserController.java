package org.javahub.submarine.modules.system.controller;

import org.javahub.submarine.common.dto.Auth;
import org.javahub.submarine.common.dto.Result;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.common.util.CommonUtil;
import org.javahub.submarine.modules.system.dto.UserDto;
import org.javahub.submarine.modules.system.entity.User;
import org.javahub.submarine.modules.system.mapstruct.UserMapStruct;
import org.javahub.submarine.modules.system.service.UserService;
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
     * 用户登录
     */
    @PostMapping("/login")
    public Result login(UserDto userDto) {
        return Result.success(20000, new Auth("admin-token"), "成功");
    }

    /**
     * 用户信息
     */
    @GetMapping("/info")
    public Result info(UserDto userDto) {
        return Result.success(20000, new Auth("admin-token"), "成功");
    }

    /**
     * 用户分页查询
     */
    @GetMapping("/list/page")
    public XPage<UserDto> findListByPage(UserDto userDto, XPage xPage) {
        XPage<User> userPage = userService.findUserList(userDto.toEntity(), xPage);
        return userPage.toDto();
    }

    /**
     * 用户查询
     */
    @GetMapping("/list/all")
    public List<UserDto> findList(UserDto userDto) {
        List<User> userList = userService.findUserList(userDto.toEntity());
        return CommonUtil.toDto(userList, UserMapStruct.class);
    }

    /**
     * 用户详情
     */
    @GetMapping("/detail")
    public UserDto getById(Long id) {
        User user = userService.getUserById(id);
        return user.toDto();
    }

    /**

     * 用户保存
     */
    @PostMapping("/save")
    public Result save(UserDto userDto) {
        userService.saveUser(userDto.toEntity());
        return Result.successMsg("保存成功");
    }

    /**
     * 用户删除
     */
    @DeleteMapping("/del")
    public Result delete(Long id) {
        userService.deleteUser(id);
        return Result.successMsg("删除成功");
    }
}
