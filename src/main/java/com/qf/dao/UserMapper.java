package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @user lao王
 * @date 2019/5/16 16:12
 * @varsion 1.0
 */
public interface UserMapper extends BaseMapper<User> {

    int updatePasswordById(@Param("id") Integer id,@Param("password") String password);

    User login(@Param("username") String username,@Param("password") String password);
}
