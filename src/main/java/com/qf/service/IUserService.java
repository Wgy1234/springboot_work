package com.qf.service;

import com.qf.entity.User;

public interface IUserService {
    User addUser(User user);

    User getEmailByUsername(String username);

    User getUserById(Integer id);

    int updatePasswordById(Integer id, String password);

    User login(String username, String password);


}
