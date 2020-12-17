package com.dev.cxf.config;

import com.dev.cxf.model.User;
import com.dev.cxf.service.Management;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;

/**
 * @Description :  webservice发布配置 //描述
 * @Author :   //作者
 */
@Configuration
public class WebServiceConfig {

    @Resource
    private Management management;

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus(){
        return new SpringBus();
    }

    /**
     * JAX-WS
     */
    @Bean
    public Endpoint endpoint(){
        EndpointImpl endpoint = new EndpointImpl(springBus(), management);
        endpoint.publish("/test");
        return endpoint;
    }

    /**
     * 访问地址 http://127.0.0.1:80/user/test?wsdl
     *
     * 但cxf 2.0.4之后的版本不需再配置此项内容，只需在application中配置
     * cxf.path=/user
     * 或者
     * cxf:
     *   path: /user
     */
//    @Bean()
//    public ServletRegistrationBean registrationBean(){
//        return new ServletRegistrationBean(new CXFServlet(),"/user/*");
//    }
}
