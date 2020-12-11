package com.dev.aop.aspect;

import com.dev.aop.annotation.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName : Log  //类名
 * @Description : spring aop  //描述
 */
@Aspect
@Component
public class LogAspect {

    /**
     * 切入点
     */
    @Pointcut(value = "@annotation(com.dev.aop.annotation.Log)")
    public void logPointcut(){
    }

    /**
     * 异常通知
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointcut()",throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint,Exception e) {
        resolver(joinPoint, null, e);
    }

    /**
     *
     * @param joinPoint
     * @param object
     */
    @AfterReturning(value = "logPointcut()",returning = "object")
    public void logAfterReturning(JoinPoint joinPoint, Object object) {
        resolver(joinPoint, object, null);
    }

    protected void resolver(JoinPoint joinPoint, Object object, Exception e) {
        try {
            Log log = (Log) getAnnotationLog(joinPoint);
            if (Objects.isNull(log)) {
                return;
            }
            /**
             * 获取登录用户session
             * 如果是使用了Shiro: User user = (User) SecurityUtils.getSubject().getPrincipal()
             */
            User user = (User) getSession().getAttribute("user");
            //假设map为log类
            Map<String,Object> map = new HashMap<String, Object>(16);
            if (Objects.nonNull(user)) {
                //操作人
                map.put("oper_user", user.getUsername());
                //部门
                map.put("oper_dep", user.getDep());
            }
            //请求地址:127.0.0.1
            //整合shiro使用 getSubject().getSession().getHost()
            map.put("ip", getHostIp());
            //状态:成功 失败
            map.put("status", 1);
            //请求URL
            map.put("req_url", getRequest().getRequestURI());
            //请求方式:GET/POST
            map.put("req_method", getRequest().getMethod());
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            map.put("oper_method", className + "." + methodName + "()");
            //返回结果
            map.put("json_result", marshal(object));
            //处理注解参数
            getAnnotationParameter(log,map);
            if (Objects.nonNull(e)) {
                //状态:成功 失败
                map.put("status", 2);
                //请求类型
                map.put("oper_errmsg",substring(e.getMessage(),0,3000));
            }

            System.out.println(map.toString());
        } catch (Exception exception) {
            exception.printStackTrace();
            System.err.println(exception.getMessage());
        }
    }

    /**
     * 注解参数处理
     * @param log
     * @param m
     */
    public void getAnnotationParameter(Log log, Map<String, Object> m) throws Exception {
        //标题
        m.put("title", log.title());
        //操作类别:INSERT、UPDATE
        m.put("business", log.type().ordinal());
        //是否保存注解参数
        if (log.isParameter()){
            getParameter(m);
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 获取请求参数
     */
    private void getParameter(Map<String, Object> map) throws Exception {
        Map<String, String[]> parameterMap = getRequest().getParameterMap();
        if (!CollectionUtils.isEmpty(parameterMap)){
            map.put("req_parameter", substring(marshal(parameterMap), 0, 3000));
        }
    }

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

    private String marshal(Object value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(value);
    }

    public static String substring(final String str, int start, int end) {

        if (str == null) {
            return "";
        }

        if (end < 0) {
            end = str.length() + 1 + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return "";
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    @Data
    public class User implements Serializable {
        private int id;
        private String username;
        private String dep;

        public User(int id, String username, String dep) {
            this.id = id;
            this.username = username;
            this.dep = dep;
        }
    }

    public static String getHostIp() {

        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

        }
        return "127.0.0.1";
    }
}
