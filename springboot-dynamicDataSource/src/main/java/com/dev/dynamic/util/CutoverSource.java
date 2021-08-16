package com.dev.dynamic.util;

import com.dev.dynamic.config.OrmContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @ClassName : cutoverSource  //类名
 * @Description :   //描述
 * @Author :   //作者
 */
@Aspect
@Component
public class CutoverSource {
    @Pointcut("@annotation(com.dev.dynamic.util.DB)")
    public void db(){
    }

    @Before("db()")
    public void cutover(JoinPoint joinPoint){
        DB db = getDB(joinPoint);
        if (Objects.nonNull(db)){
            if (DataSourceType.MASTER == db.dataSource()) {
                OrmContextHolder.master();
            }

            if (DataSourceType.SLAVE == db.dataSource()) {
                OrmContextHolder.slave();
            }
        }
    }

    private DB getDB(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return Objects.nonNull(method) ? method.getAnnotation(DB.class) : null;
    }
}
