package com.dev.dynamic.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ClassName : CustomizeDataSource  //类名
 * @Description :   //描述
 * @Author :   //作者
 */
public class CustomizeDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return OrmContextHolder.get();
    }
}
