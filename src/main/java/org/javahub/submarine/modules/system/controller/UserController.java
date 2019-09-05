package org.javahub.submarine.modules.system.controller;

import org.javahub.submarine.common.dto.Result;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.common.util.CommonUtil;
import org.javahub.submarine.modules.system.dto.UserDto;
import org.javahub.submarine.modules.system.entity.User;
import org.javahub.submarine.modules.system.mapstruct.UserMapStruct;
import org.javahub.submarine.modules.system.service.UserService;
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
        XPage<User> userPage = userService.findUserList(userDto.toEntity(), xPage);
        return userPage.toDto();
    }

    /**
     * 查询
     */
    @PreAuthorize("hasAnyAuthority('user.find')")
    @GetMapping("/list/all")
    public List<UserDto> findList(UserDto userDto) {
        List<User> userList = userService.findUserList(userDto.toEntity());
        return CommonUtil.toDto(userList, UserMapStruct.class);
    }

    /**
     * 详情
     */
    @PreAuthorize("hasAnyAuthority('user.find')")
    @GetMapping("/detail")
    public UserDto getById(long id) {
        User user = userService.getUserById(id);
        return user.toDto();
    }

    /**
     * 保存
     */
    @PreAuthorize("hasAnyAuthority('user.add', 'user.edit')")
    @PostMapping("/save")
    public Result save(UserDto userDto) {
        String pass = userService.saveUser(userDto.toEntity());
        return Result.success(pass, "保存成功");
    }

    /**
     * 删除
     */
    @PreAuthorize("hasAnyAuthority('user.del')")
    @DeleteMapping("/del")
    public Result delete(UserDto userDto) {
        userService.deleteUser(userDto.getId());
        return Result.successMsg("删除成功");
    }
}
