package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @user lao王
 * @date 2019/5/16 16:10
 * @varsion 1.0
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User addUser(User user) {
        userMapper.insert(user);
        return user;
    }

    @Override
    public User getEmailByUsername(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User getUserById(Integer id) {

        return userMapper.selectById(id);
    }

    @Override
    public int updatePasswordById(Integer id, String password) {

        return userMapper.updatePasswordById(id,password);
    }

    @Override
    public User login(String username, String password) {

        return userMapper.login(username,password);
    }
}
