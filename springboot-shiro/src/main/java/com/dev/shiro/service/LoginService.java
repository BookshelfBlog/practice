package com.dev.shiro.service;

import com.dev.shiro.entity.Login;

import java.util.Set;

/**
 * @author admin //作者
 */
public interface LoginService {
    /**
     * 根据用户登录名查询
     *
     * @param loginName 登录名
     *
     * @return Login 用户账户
     */
    Login login(String loginName);

    /**
     * 根据登录名校验
     *
     * @param loginName 登录名
     *
     * @return int 账户效验
     */
    int checkLoginName(String loginName);

    /**
     * 获取角色
     *
     * @param loginName 登录名
     *
     * @return Set<String> 角色列表
     */
    Set<String> getRoles(String loginName);

    /**
     * 获取权限
     *
     * @param loginName 登录名
     *
     * @return Set<String> 权限列表
     */
    Set<String> getPermissions(String loginName);
}
