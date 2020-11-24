package com.dev.mybatis.service.impl;

import com.dev.mybatis.entity.User;
import com.dev.mybatis.mapper.UserDao;
import com.dev.mybatis.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName : UserServiceImpl  //类名
 * @Description : 用户业务层  //描述
 * @Author : hao niu  //作者
 * @Date: 2020-11-17 10:52  //时间
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    /**
     * 获取用户列表
     *
     * @param user 用户
     * @return List<User> 用户集合
     */
    @Override
    public List<User> getUserList(User user) {
        return userDao.getUserList(user);
    }

    /**
     * 根据主键获取单个用户
     *
     * @param id 用户主键
     * @return User 用户
     */
    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    /**
     * 新增用户
     *
     * @param user 用户
     * @return boolean 操作标识
     */
    @Override
    public boolean insertUserSelective(User user) {
        return userDao.insertUserSelective(user);
    }

    /**
     * 更新用户
     *
     * @param user 用户
     * @return boolean 操作标识
     */
    @Override
    public boolean updateUserSelective(User user) {
        return userDao.updateUserSelective(user);
    }

    /**
     * 根据主键删除用户
     *
     * @param id 用户主键
     * @return boolean 操作标识
     */
    @Override
    public boolean removeById(int id) {
        return userDao.removeById(id);
    }

    /**
     * 根据用户属性删除用户
     *
     * @param user 用户
     * @return boolean 操作标识
     */
    @Override
    public boolean removeBySelective(User user) {
        return userDao.removeBySelective(user);
    }
}
