package com.dev.amqp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Description : 配置类  //描述
 */
@Configuration
@Slf4j
public class AmqpConfig {

    @Resource
    RabbitTemplate rabbitTemplate;

    @Bean
    public AmqpTemplate getInstance(){
        //使用Jackson 消息转换器
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
        /**
         * Mandatory：
         *  为true时,消息通过交换器无法匹配到队列会返回给生产者并触发MessageReturn
         *  为false时,匹配不到会直接被丢弃
         * 设置方式：
         *  在yml中配置:publisher-returns: true
         *  在properts中配置:spring.rabbitmq.publisher-returns=true
         *  在java类中配置:rabbitTemplate.setMandatory(true);
         *
         *  本项目已经在yml中配置了所以这里不用设置
         */
        //rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("message[{}] success to send!", correlationData.getId());
            } else {
                log.error("message[{}] Failed to send,the reason[{}]", correlationData.getId(), cause);
            }
            log.info("{},ack:[{}]", correlationData, ack);
        });
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            log.info("ReturnsCallback:{}",returnedMessage.toString());
//            System.out.println("ReturnCallback:     " + "消息：" + returnedMessage.getMessage());
//            System.out.println("ReturnCallback:     " + "回应码：" + returnedMessage.getReplyCode());
//            System.out.println("ReturnCallback:     " + "回应信息：" + returnedMessage.getReplyText());
//            System.out.println("ReturnCallback:     " + "路由键：" + returnedMessage.getRoutingKey());
//            System.out.println("ReturnCallback:     " + "交换机：" + returnedMessage.getExchange());
        });
        return rabbitTemplate;
    }
}
