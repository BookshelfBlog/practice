package com.dev.amqp.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Description : 扇面交换机  //描述
 */
@Configuration
public class FanoutMode {

    @Value("${amqp-config.fanout.one.queue}")
    private String QUEUEA;
    @Value("${amqp-config.fanout.exchange}")
    private String F_EXCHANGE;
    @Value("${amqp-config.fanout.two.queue}")
    private String QUEUEB;

    @Bean
    Queue queuea(){
        return QueueBuilder.durable(QUEUEA).build();
    }

    @Bean
    Queue queueb(){
        return QueueBuilder.durable(QUEUEB).build();
    }

    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange(F_EXCHANGE);
    }

    @Bean
    Binding bindinga(){
        return BindingBuilder.bind(queuea()).to(fanoutExchange());
    }

    @Bean
    Binding bindingb(){
        return BindingBuilder.bind(queueb()).to(fanoutExchange());
    }
}
