package com.dev.mybatisPlus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : CodeGenerator  类名
 * @Description :   描述
 * @Author : niuhao  作者
 */
public class CodeGenerator {
    public static void main(String[] args) throws InterruptedException {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/springboot-mybatis-plus/src/main/java");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        gc.setOpen(false);
        gc.setSwagger2(true);
        gc.setAuthor("hao niu");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sDao");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sImpl");
        gc.setControllerName("%sController");

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType( DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://localhost/simple?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&useSSL=false");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.dev.mybatisPlus")
                .setController( "controller")
                .setEntity( "entity" )
                .setService("service")
                .setServiceImpl("service.impl")
                .setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/vm/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/springboot-mybatis-plus/src/main/resources/mybatis/"
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        focList.add(new FileOutConfig("/templates/vm/controller.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/springboot-mybatis-plus/src/main/java/com/dev/mybatisPlus/controller/"
                        + tableInfo.getEntityName() + "Controller" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/templates/vm/entity.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/springboot-mybatis-plus/src/main/java/com/dev/mybatisPlus/entity/"
                        + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        //templateConfig.setXml("\\templates\\vm\\mapper.xml");
        templateConfig.setXml(null);
        templateConfig.setController("/templates/vm/controller.java");
        templateConfig.setEntity("/templates/vm/entity.java");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //此处可以修改为您的表前缀
        strategy.setTablePrefix(new String[] { ""});
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        strategy.setInclude(new String[] { "user" });
        // 排除生成的表
        //strategy.setExclude(new String[]{"test"});
        strategy.setSuperControllerClass("com.dev.mybatisPlus.controller.BaseController");
        strategy.setEntityLombokModel( true );

        mpg.setStrategy(strategy);

        // 执行生成
        mpg.execute();
    }
}
