package com.dev.swagger.config;

import com.dev.swagger.util.IpUtils;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * @ClassName : SwaggerConfig  //类名
 * @Description : swagger配置  //描述
 * @Author : hao niu  //作者
 * @Date: 2020-11-18 11:56  //时间
 */
@Configuration
@ComponentScan(basePackages = "com.dev.swagger.controller")
public class SwaggerConfig {
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.OAS_30)
//                //是否开启，根据环境配置
//                .enable(true)
//                .groupName("后台接口")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.dev.swagger"))
//                .paths(PathSelectors.any())
//                .build();
//    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("后台接口")
                .pathsToMatch("/**")
                .packagesToScan("com.dev.swagger")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Swagger3接口文档")
                        .description("测试swagger3文档")
                        .version("v1.0")
                        .contact(new Contact().name("Hao Niu").url(IpUtils.getHostIp()))
                        .license(new License().name("Hao Niu").url(IpUtils.getHostIp())))
                        .externalDocs(new ExternalDocumentation()
                            .description("null")
                            .url("null")
                        );
    }

//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Swagger3接口文档")
//                .description("springboot-swagger3")
//                .contact(new Contact("Hao Niu", IpUtils.getHostIp(), "xxx.@163.com"))
//                .version("1.0")
//                .build();
//    }
}
