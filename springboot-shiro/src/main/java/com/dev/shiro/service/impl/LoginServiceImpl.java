package com.dev.shiro.service.impl;

import com.dev.shiro.entity.Login;
import com.dev.shiro.mapper.LoginDao;
import com.dev.shiro.service.LoginService;
import com.dev.shiro.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Set;

/**
 * @ClassName : LoginServiceImpl  //类名
 * @Description : 登录实现类  //描述
 *
 * @Date: 2020-12-07 16:50  //时间
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginDao loginDao;

    /**
     * 根据用户登录名、用户名、密码查询
     *
     * @param loginName 登录名
     * @return Login 用户账户
     */
    @Override
    public Login login(String loginName) {
        if (Objects.isNull(loginName) || checkLoginName(loginName) == 0){
            log.error("user login error: parm loginName is empty!");
            return null;
        }
        return loginDao.queryUserByLoginNameOrUserNameAndPwd(loginName);
    }

    /**
     * 根据登录名校验
     *
     * @param loginName 登录名
     * @return int 账户效验
     */
    @Override
    public int checkLoginName(String loginName) {
        return loginDao.countUserByLoginName(loginName);
    }

    /**
     * 获取角色
     *
     * @param loginName 登录名
     * @return Set<String> 角色列表
     */
    @Override
    public Set<String> getRoles(String loginName) {
        return loginDao.getRoles(loginName);
    }

    /**
     * 获取权限
     *
     * @param loginName 登录名
     * @return Set<String> 权限列表
     */
    @Override
    public Set<String> getPermissions(String loginName) {
        return loginDao.getPermissions(loginName);
    }
}
