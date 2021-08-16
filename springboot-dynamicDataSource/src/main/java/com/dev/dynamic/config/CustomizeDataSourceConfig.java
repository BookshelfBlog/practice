package com.dev.dynamic.config;

import com.dev.dynamic.util.DataSourceType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : DataSourceConfig  //类名
 * @Description : 数据源配置  //描述
 * @Author :   //作者
 * @Date: 2021-08-16 10:43  //时间
 */
@Configuration
public class CustomizeDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    public DataSource slaveDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource routingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource, @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        Map<Object, Object> map = new HashMap<>(5);
        map.put(DataSourceType.MASTER, masterDataSource);
        map.put(DataSourceType.SLAVE, slaveDataSource);
        CustomizeDataSource customizeDataSource = new CustomizeDataSource();
        customizeDataSource.setDefaultTargetDataSource(masterDataSource);
        customizeDataSource.setTargetDataSources(map);
        return masterDataSource;
    }
}
