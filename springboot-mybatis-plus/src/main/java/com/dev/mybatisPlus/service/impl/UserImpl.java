package com.dev.mybatisPlus.service.impl;

import com.dev.mybatisPlus.entity.User;
import com.dev.mybatisPlus.mapper.UserMapper;
import com.dev.mybatisPlus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户管理 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-06-01
 */
@Service
public class UserImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
