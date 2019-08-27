package org.javahub.submarine.modules.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.User;
import org.javahub.submarine.modules.system.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Resource
    private UserMapper userMapper;

    @Transactional(readOnly = true)
    public XPage<User> findUserList(User user, XPage xPage) {
        XPage<User> userList = userMapper.findPage(xPage, user);
        return userList;
    }

    @Transactional(readOnly = true)
    public List<User> findUserList(User user) {
        return userMapper.findList(user);
    }

    @Transactional
    public void saveUser(User user) {
        super.saveOrUpdate(user);
    }

    @Transactional
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Transactional
    public void deleteUser(Long id) {
        super.removeById(id);
    }
}
