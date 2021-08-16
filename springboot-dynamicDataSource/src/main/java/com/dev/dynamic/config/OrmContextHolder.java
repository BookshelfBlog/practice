package com.dev.dynamic.config;

import com.dev.dynamic.util.DataSourceType;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName : OrmContextHolder  //类名
 * @Description :   //描述
 * @Author :   //作者
 */
public class OrmContextHolder {

    private static final ThreadLocal<DataSourceType> tl = new ThreadLocal<>();
    private static final AtomicInteger at = new AtomicInteger(-1);

    public static void set(DataSourceType dataSourceType) {
        tl.set(dataSourceType);
    }

    public static DataSourceType get() {
        return tl.get();
    }

    public static void master() {
        set(DataSourceType.MASTER);
        System.out.println("切换至master");
    }

    public static void slave() {
        //  轮询
        int index = at.getAndIncrement() % 2;
        if (at.get() > 9999) {
            at.set(-1);
        }
        if (index == 0) {
            set(DataSourceType.SLAVE);
            System.out.println("切换到slave1");
        }
    }
}
