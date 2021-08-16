package com.dev.dynamic.util;

import java.lang.annotation.*;

/**
 * @author Administrator
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DB {

    DataSourceType dataSource() default DataSourceType.MASTER;
}
