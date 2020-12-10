package com.dev.shiro.config;

import com.dev.shiro.shiro.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName : ShiroConfig  //类名
 * @Description : shiro配置  //描述
 * @Author : Hao Niu //作者
 * @Date: 2020-12-07 14:51  //时间
 */
@Configuration
public class ShiroConfig {

    @Value("${shiro.loginUrl}")
    private String loginUrl;
    @Value("${shiro.indexUrl}")
    private String indexUrl;
    @Value("${shiro.unauthorizedUrl}")
    private String unauthorizedUrl;

    @Bean
    public SecurityManager securityManager(UserRealm userRealm)
    {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 自定义 userRealm
     */
    @Bean
    public UserRealm userRealm()
    {
        UserRealm userRealm = new UserRealm();
        return userRealm;
    }

    /**
     * 退出过滤器
     */
    public LogoutFilter logoutFilter()
    {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl(loginUrl);
        return logoutFilter;
    }

    /**
     * Shiro过滤器配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager)
    {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 身份认证失败，则跳转到登录页面的配置
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        shiroFilterFactoryBean.setSuccessUrl(indexUrl);
        // 权限认证失败，则跳转到指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        // Shiro连接约束配置，即过滤链的定义
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 退出 logout地址，shiro去清除session
        filterChainDefinitionMap.put("/logout", "logout");
        // “/system” 开头的需要身份认证
        filterChainDefinitionMap.put("/system*", "authc");
        // “/mobile” 开头的需要角色认证，是“mobile”才允许
        filterChainDefinitionMap.put("/mobile*", "roles[mobile]");
        // “/platform” 开头的需要权限认证，是“platform:view”才允许
        filterChainDefinitionMap.put("/platform*", "perms[\"platform:view\"]");
        // 不需要拦截的访问
        filterChainDefinitionMap.put("/login", "anon");

        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        // 注销成功，则跳转到指定页面
        filters.put("logout", logoutFilter());
        shiroFilterFactoryBean.setFilters(filters);

        // 所有请求需要认证
        filterChainDefinitionMap.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }
}
