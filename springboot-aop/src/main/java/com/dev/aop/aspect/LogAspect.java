package com.dev.aop.aspect;

import com.dev.aop.annotation.Log;
import com.dev.aop.util.ServletUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description : spring aop  //描述
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    /* 常量 */
    private static final String HTTP_POST = "POST";
    private static final String HTTP_GET = "GET";

    /**
     * 切入点
     */
    @Pointcut(value = "@annotation(com.dev.aop.annotation.Log)")
    public void logPointcut(){
    }

    /**
     * 后置处理(方法返回时触发)
     * @param joinPoint
     * @param object
     */
    @AfterReturning(value = "logPointcut()",returning = "object")
    public void logAfterReturning(JoinPoint joinPoint, Object object) {
        resolver(joinPoint, object, null);
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
     * 解析参数，保存日志
     * @param joinPoint
     * @param object
     * @param e
     */
    protected void resolver(final JoinPoint joinPoint,final Object object, Exception e) {
        try {
            //获取注解
            Log log = (Log) getAnnotationLog(joinPoint);
            if (Objects.isNull(log)) {
                return;
            }
            /**
             * 获取登录用户session
             * 如果整合shiro使用： User user = (User) SecurityUtils.getSubject().getPrincipal()
             */
            User user = (User) ServletUtil.getSession().getAttribute("user");
            //假设map为log类
            Map<String,Object> map = new HashMap<String, Object>(16);
            if (Objects.nonNull(user)) {
                //操作人
                map.put("oper_user", user.getUsername());
                //部门
                map.put("oper_dep", user.getDep());
            }
            //请求主机ip:127.0.0.1
            //如果整合shiro使用：getSubject().getSession().getHost()
            map.put("ip", getHostIp());
            //状态:成功/失败
            map.put("status", 1);
            //请求URL
            map.put("req_url", ServletUtil.getRequest().getRequestURI());
            //请求方式:GET/POST
            map.put("req_method", ServletUtil.getRequest().getMethod());
            //类名
            String className = joinPoint.getTarget().getClass().getName();
            //方法名称
            String methodName = joinPoint.getSignature().getName();
            //设置方法名称
            map.put("oper_method", className + "." + methodName + "()");
            //返回结果
            map.put("json_result", marshal(object));
            //处理注解参数
            getAnnotationParameter(log,map,joinPoint);
            if (Objects.nonNull(e)) {
                //状态:成功/失败
                map.put("status", 2);
                //异常信息
                map.put("err_msg", substring(e.toString(), 0, 3000));
            }
            //------------------------异步插入数据库-----------------------------
            System.out.println(map.toString());
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println(exception.getMessage());
        }
    }

    /**
     * 注解参数处理
     */
    public void getAnnotationParameter(Log log, Map<String, Object> m, JoinPoint joinPoint) throws Exception {
        //标题
        m.put("title", log.title());
        //操作类别:INSERT、UPDATE
        m.put("business", log.type().ordinal());
        //是否保存请求参数
        if (log.isParameter()){
            getParameter(m,joinPoint);
        }
    }

    /**
     * 获取注解
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
     * 获取并解析请求参数
     */
    private void getParameter(Map<String, Object> map, JoinPoint joinPoint) throws Exception {
        String method = ServletUtil.getRequest().getMethod();
        //POST
        if (HTTP_POST.equals(method)) {
            Object[] args = joinPoint.getArgs();
            if (args != null || args.length > 0){
                map.put("req_parameter", substring(marshal(args), 0, 3000));
            }
        }
        //GET
        if (HTTP_GET.equals(method)) {
            Map<String, String[]> parameterMap = ServletUtil.getRequest().getParameterMap();
            if (!CollectionUtils.isEmpty(parameterMap)) {
                map.put("req_parameter", substring(marshal(parameterMap), 0, 3000));
            }
        }
    }

    private String marshal(Object value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(value);
    }

    /**
     * 截取字符串
     */
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

    /**
     * 获取ip
     */
    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

        }
        return "127.0.0.1";
    }
}
