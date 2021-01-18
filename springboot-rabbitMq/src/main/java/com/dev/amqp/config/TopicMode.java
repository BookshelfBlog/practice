package com.dev.amqp.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description : 主题交换机  //描述
 */
@Configuration
public class TopicMode {

    @Value("${amqp-config.topic.student.queue}")
    private String STUDENT;
    @Value("${amqp-config.topic.teacher.queue}")
    private String TEACHER;
    @Value("${amqp-config.topic.exchange}")
    private String T_EXCHANGE;
    @Value("${amqp-config.topic.routing}")
    private String ROUTING;

    @Bean
    Queue student(){
        return QueueBuilder.durable(STUDENT).build();
    }

    @Bean
    Queue teacher(){
        return QueueBuilder.durable(TEACHER).build();
    }

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(T_EXCHANGE);
    }

    @Bean
    Binding studentBinding(){
        return BindingBuilder.bind(student()).to(topicExchange()).with(STUDENT);
    }

    @Bean
    Binding teacherBinding(){
        return BindingBuilder.bind(teacher()).to(topicExchange()).with(ROUTING);
    }
}
