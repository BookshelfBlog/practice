package com.dev.shiro.shiro;

import com.dev.shiro.entity.Login;
import com.dev.shiro.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @Description : 用户配置  //描述
 * @Author : Hao Niu  //作者
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    //
    @Autowired
    private LoginService loginService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        Login login = (Login) pc.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (Objects.nonNull(login)){
            info.setRoles(loginService.getRoles(login.getLoginName()));
            info.setStringPermissions(loginService.getPermissions(login.getLoginName()));
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String loginName = (String) token.getPrincipal();
        if (loginName.isEmpty() && loginName.isBlank()) {
            return null;
        }
        Login login = loginService.login(loginName);
        if(Objects.nonNull(login)){
            AuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(login, token.getCredentials(), getName());
            return simpleAuthenticationInfo;
        }else {
            return null;
        }
    }
}
