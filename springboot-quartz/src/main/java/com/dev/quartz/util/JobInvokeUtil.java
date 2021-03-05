package com.dev.quartz.util;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName : JobInvokeUtil  //类名
 * @Description :   //描述
 * @Author :   //作者
 * @Date: 2021-02-25 16:11  //时间
 */
public class JobInvokeUtil {

    /**
     * 执行job任务
     */
    @SneakyThrows
    public static void invokeMethod(com.dev.quartz.entity.Job job) {
        String invokeTarget = job.getInvokeTarget();
        String beanName = getBeanName(invokeTarget);
        String methodName = getMethodName(invokeTarget);
        List<Object[]> methodParams = getMethodParams(invokeTarget);
        if (!isValidClassName(beanName)){
            Object bean = SpringUtils.getBean(beanName);
            invokeMethod(bean,methodName,methodParams);
        }else{
            Object o = Class.forName(beanName).getDeclaredConstructor().newInstance();
            invokeMethod(o,methodName,methodParams);
        }
    }

    public static void invokeMethod(Object bean, String methodName, List<Object[]> methodParams) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (CollectionUtils.isNotEmpty(methodParams)) {
            Method declaredMethod = bean.getClass().getDeclaredMethod(methodName, getParamsType(methodParams));
            declaredMethod.invoke(bean, getParamsValue(methodParams));
        } else {
            Method declaredMethod = bean.getClass().getDeclaredMethod(methodName);
            declaredMethod.invoke(bean);
        }
    }

    /**
     * 获取method方法参数相关列表
     *
     * @param invokeTarget 目标字符串
     * @return method方法相关参数列表
     */
    public static List<Object[]> getMethodParams(String invokeTarget)
    {
        String methodStr = StringUtils.substringBetween(invokeTarget, "(", ")");
        if (StringUtils.isEmpty(methodStr))
        {
            return null;
        }
        String[] methodParams = methodStr.split(",");
        List<Object[]> classs = new LinkedList<>();
        for (int i = 0; i < methodParams.length; i++)
        {
            String str = StringUtils.trimToEmpty(methodParams[i]);
            // String字符串类型，包含'
            if (StringUtils.contains(str, "'"))
            {
                classs.add(new Object[] { StringUtils.replace(str, "'", ""), String.class });
            }
            // boolean布尔类型，等于true或者false
            else if (StringUtils.equals(str, "true") || StringUtils.equalsIgnoreCase(str, "false"))
            {
                classs.add(new Object[] { Boolean.valueOf(str), Boolean.class });
            }
            // long长整形，包含L
            else if (StringUtils.containsIgnoreCase(str, "L"))
            {
                classs.add(new Object[] { Long.valueOf(StringUtils.replaceIgnoreCase(str, "L", "")), Long.class });
            }
            // double浮点类型，包含D
            else if (StringUtils.containsIgnoreCase(str, "D"))
            {
                classs.add(new Object[] { Double.valueOf(StringUtils.replaceIgnoreCase(str, "D", "")), Double.class });
            }
            // 其他类型归类为整形
            else
            {
                classs.add(new Object[] { Integer.valueOf(str), Integer.class });
            }
        }
        return classs;
    }

    /**
     * 获取bean名称
     */
    public static String getBeanName(String invokeTarget){
        if (checkStr(invokeTarget)) {
            return "";
        }
        String str = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringBeforeLast(str, ".");
    }

    /**
     * 获取方法名称
     */
    public static String getMethodName(String invokeTarget){
        if (checkStr(invokeTarget)) {
            return "";
        }
        String str = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringAfterLast(str, ".");
    }

    /**
     * 判断字符串是否为空
     * str is null or length = 0 return true,otherwise return false
     * @param str
     * @return
     */
    public static boolean checkStr(String str){
        if (StringUtils.isBlank(str) || StringUtils.isEmpty(str)) {
            return true;
        }
        return false;
    }

    /**
     * 校验是否是合法的类名
     * @param invokeTarget
     * @return boolean
     */
    public static boolean isValidClassName(String invokeTarget){
        return StringUtils.countMatches(invokeTarget, ".") > 1;
    }

    /**
     * 获取参数类型
     * @param methodParams
     * @return Class<?>[]
     */
    public static Class<?>[] getParamsType(List<Object[]> methodParams) {
        Class<?>[] classes = new Class<?>[methodParams.size()];
        int i = 0;
        for (Object[] o:methodParams) {
            classes[i] = (Class<?>) o[1];
            i++;
        }
        return classes;
    }

    /**
     * 获取参数值
     * @param methodParams
     * @return object[]
     */
    public static Object[] getParamsValue(List<Object[]> methodParams) {
        Object[] objects = new Object[methodParams.size()];
        int index = 0;
        for (Object[] o:methodParams) {
            objects[index] = (Object) o[0];
            index++;
        }
        return objects;
    }
}
