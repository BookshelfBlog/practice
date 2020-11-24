package com.dev.mybatis.service;

import com.dev.mybatis.entity.User;

import java.util.List;

public interface UserService {

    /**
     * 获取用户列表
     * @param user 用户
     * @return List<User> 用户集合
     */
    List<User> getUserList(User user);

    /**
     * 根据主键获取单个用户
     * @param id 用户主键
     * @return User 用户
     */
    User getUserById(int id);

    /**
     * 新增用户
     * @param user 用户
     * @return boolean 操作标识
     */
    boolean insertUserSelective(User user);

    /**
     * 更新用户
     * @param user 用户
     * @return boolean 操作标识
     */
    boolean updateUserSelective(User user);

    /**
     * 根据主键删除用户
     * @param id 用户主键
     * @return boolean 操作标识
     */
    boolean removeById(int id);

    /**
     * 根据用户属性删除用户
     * @param user 用户
     * @return boolean 操作标识
     */
    boolean removeBySelective(User user);
}
