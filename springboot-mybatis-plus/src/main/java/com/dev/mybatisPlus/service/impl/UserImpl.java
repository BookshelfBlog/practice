package com.dev.mybatisPlus.service.impl;

import com.dev.mybatisPlus.entity.User;
import com.dev.mybatisPlus.mapper.UserDao;
import com.dev.mybatisPlus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @since 2020-11-18
 */
@Service
public class UserImpl extends ServiceImpl<UserDao, User> implements UserService {

}
