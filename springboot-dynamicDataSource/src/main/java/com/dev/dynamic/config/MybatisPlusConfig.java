package com.dev.dynamic.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @ClassName : MybatisPlusConfig  //类名
 * @Description :   //描述
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.dev.dynamic.mapper")
public class MybatisPlusConfig {

    @Resource(name = "routingDataSource")
    private DataSource routingDataSource;

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }

    /**
     * 配置mybatis plug SqlSessionFactory
     *
     * @return (MybatisSqlSessionFactoryBean) sqlSessionFactoryBean
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(routingDataSource);
        /*
         * 此处配置和yml中配置二选一即可
         * mybatis-plus:
         *   mapper-locations: com.dev.dynamic.mapper.xml.*Mapper.xml
         */
        //sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("com.dev.dynamic.mapper.xml.*Mapper.xml"));
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        sqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
        return sqlSessionFactoryBean.getObject();
    }
}
