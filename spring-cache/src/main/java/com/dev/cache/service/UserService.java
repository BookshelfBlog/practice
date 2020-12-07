package com.dev.cache.service;

import com.dev.cache.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author hao niu
 * @since 2020-11-27
 */
public interface UserService extends IService<User> {

    List<User> list(User user);
}
