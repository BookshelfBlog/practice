package com.dev.cache.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dev.cache.entity.User;
import com.dev.cache.mapper.UserDao;
import com.dev.cache.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * 用户信息 服务实现类
 *
 * @since 2020-11-27
 */
@Service
@CacheConfig(cacheNames = "user")
public class UserImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Override
    public List<User> list(User entity) {
        if (Objects.nonNull(entity)){
            QueryWrapper<User> objectQueryWrapper = new QueryWrapper<User>();
            objectQueryWrapper.setEntity(entity);
            return super.list(objectQueryWrapper);
        }
        return super.list();
    }

    @Override
    @CachePut(key = "#entity.id" , unless = "#result == null")
    public boolean save(User entity) {
        return super.save(entity);
    }

    @Override
    @Cacheable(key = "#id", unless = "#result == null")
    public User getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    @CacheEvict(key = "#entity.id")
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }

    @Override
    @CacheEvict(key = "#id")
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
