package com.dev.tools.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author niu hao
 * @describe
 * @date 2022-02-2022/2/23
 */
@Slf4j
public class ReflectionUtil {

    public static Class<?> getClazz(String name) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public static Object newInstance(Class<?> clazz){
        Object o = null;
        try {
            o = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static Method getMethod(Class<?> clazz,String methodName,Class<?>... parameterTypes){
        Method method = null;
        try {
            method = clazz.getMethod(methodName,parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    public static Object invokeMethod(Method method, Object o1,Object... o2) {
        Object result = null;
        try {
            result = method.invoke(o1, o2);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = getClazz("com.dev.tools.util.C");
        Object test1 = invokeMethod(getMethod(clazz, "test1", String.class), newInstance(clazz), "hello");
        System.out.println(test1);
    }
}
