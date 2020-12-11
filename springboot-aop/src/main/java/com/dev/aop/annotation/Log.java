package com.dev.aop.annotation;

import java.lang.annotation.*;

/**
 * @author admin
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 标题
     */
    String title() default "";

    /**
     * 类型
     */
    BusinessType type() default BusinessType.OTHER;

    /**
     * 是否保存参数
     */
    boolean isParameter() default false;

    enum BusinessType{
        OTHER,INSERT,UPDATE,REMOVE;
    }
}
