package com.dev.aop.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description :  Servlet工具类 //描述
 */
public class ServletUtil {
    /**
     * 获取request
     */
    public static HttpServletRequest getRequest(){
        HttpServletRequest request = getServletRequestAttributes().getRequest();
        return request;
    }

    /**
     * 获取session
     */
    public static HttpSession getSession(){
        HttpSession session = getServletRequestAttributes().getRequest().getSession();
        return session;
    }

    /**
     * 获取getServletRequestAttributes
     */
    public static ServletRequestAttributes getServletRequestAttributes(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes;
    }
}
