package com.dev.amqp.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description : 延时队列   //描述
 */
@Configuration
public class DelayQueueMode {

    @Value("${amqp-config.delay.delay}")
    private String delay;
    @Value("${amqp-config.delay.deadLetter}")
    private String delayLetter;
    @Value("${amqp-config.delay.delayExchange}")
    private String delayExchange;
    @Value("${amqp-config.delay.dlxExchange}")
    private String dlxExchange;
    @Value("${amqp-config.delay.delayRouting}")
    private String delayRouting;
    @Value("${amqp-config.delay.dlxRoutingKey}")
    private String dlxRoutingKey;

    @Bean
    Queue delay(){
        Map<String, Object> args = new HashMap<>(3);
        // x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", dlxExchange);
        // x-dead-letter-routing-key  deadLetterExchange
        args.put("x-dead-letter-routing-key", dlxRoutingKey);
        //设置超时时间 10 s
        args.put("x-message-ttl", 10000);
        //设置队列长度
        args.put("x-max-length", 5);
        //队列名 是否持久化 是否排他 是否自动删除 其他参数
        return QueueBuilder.durable(delay).withArguments(args).build();
    }

    /**
     * 死信接收队列
     */
    @Bean
    Queue deadLetter(){
        return QueueBuilder.durable(delayLetter).build();
    }

    @Bean
    DirectExchange delayExchange(){
        return new DirectExchange(delayExchange,true,false);
    }

    @Bean
    Binding delayBinding(){
        return BindingBuilder.bind(delay()).to(delayExchange()).with(delayRouting);
    }

    /**
     * 死信交换机
     */
    @Bean
    DirectExchange deadLetterExchange(){
        return new DirectExchange(dlxExchange,true,false);
    }

    @Bean
    Binding deadLetterBinding(){
        return BindingBuilder.bind(deadLetter()).to(deadLetterExchange()).with(dlxRoutingKey);
    }
}
