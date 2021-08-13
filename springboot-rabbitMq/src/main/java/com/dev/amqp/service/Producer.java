package com.dev.amqp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @Description : 模拟生产者  //描述
 */
@Component
@Slf4j
public class Producer {
    /**
     * 直连交换机
     */
    @Value("${amqp-config.direct.queue}")
    private String QUEUE;
    @Value("${amqp-config.direct.exchange}")
    private String D_EXCHANGE;
    @Value("${amqp-config.direct.routingKey}")
    private String ROUTINGKEY;

    /**
     * 扇形交换机
     */
    @Value("${amqp-config.fanout.exchange}")
    private String EXCHANGE;

    /**
     * 主题交换机
     */
    @Value("${amqp-config.topic.student.queue}")
    private String STUDENT;
    @Value("${amqp-config.topic.teacher.queue}")
    private String TEACHER;
    @Value("${amqp-config.topic.exchange}")
    private String T_EXCHANGE;
    @Value("${amqp-config.topic.routing}")
    private String ROUTING;

    /**
     * 死信队列
     */
    @Value("${amqp-config.delay.delayExchange}")
    private String delayExchange;
    @Value("${amqp-config.delay.delayRouting}")
    private String delayRouting;

    /**
     * 死信接收队列
     */
    @Value("${amqp-config.delay.dlxExchange}")
    private String dlxExchange;
    @Value("${amqp-config.delay.dlxRoutingKey}")
    private String dlxRoutingKey;

    /**
     * 延时队列
     */
    @Value("${amqp-config.delay.dpExchange}")
    private String dpExchange;
    @Value("${amqp-config.delay.dpRoutingKey}")
    private String dpRoutingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 默认交换机
     * 交换机为空会发送到默认交换机上，默认（直连）交换机绑定所有队列，所以会直接发送到指定队列
     */
    public void direct(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("this message comes from direct exchange,time:").append(LocalDateTime.now());
        rabbitTemplate.convertAndSend("", QUEUE, stringBuffer.toString(), create());
    }

    /**
     * 直连交换机
     * 先根据绑定键发送至绑定的直连交换机上，直连交换机根据路由键发送至队列
     */
    public void directB(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("this message comes from direct queueB exchange,time:").append(LocalDateTime.now());
        rabbitTemplate.convertAndSend(D_EXCHANGE, ROUTINGKEY, stringBuffer.toString(), create());
    }

    /**
     * 扇面交换机（广播）
     * 根据绑定键发送至扇面交换机，交换机广播至所有绑定队列
     */
    public void fanout(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("this message comes from fanout exchange,time:").append(LocalDateTime.now());
        rabbitTemplate.convertAndSend(EXCHANGE,null,stringBuffer.toString(), create());
    }

    /**
     * 主题交换机
     * 根据绑定键发送至主题交换机，主题交换机根据路由键跟队列进行匹配，只发送至匹配成功的队列
     */
    public void student(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("this message comes from topic exchange student,time:").append(LocalDateTime.now());
        rabbitTemplate.convertAndSend(T_EXCHANGE,STUDENT,stringBuffer.toString(), create());
    }

    /**
     * 主题交换机
     * 根据绑定键发送至主题交换机，主题交换机根据路由键跟队列进行匹配，只发送至匹配成功的队列
     */
    public void teacher(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("this message comes from topic exchange teacher,time:").append(LocalDateTime.now());
        rabbitTemplate.convertAndSend(T_EXCHANGE,TEACHER,stringBuffer.toString(), create());
    }

    /**
     * 主题交换机
     * 根据绑定键发送至主题交换机，主题交换机根据路由键跟队列进行匹配，只发送至匹配成功的队列
     */
    public void topic(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("this message comes from topic exchange,time:").append(LocalDateTime.now());
        rabbitTemplate.convertAndSend(T_EXCHANGE,ROUTING,stringBuffer.toString(), create());
    }

    /**
     * 死信队列
     */
    public void delay(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("this message comes from dead Exchange,time:").append(LocalDateTime.now());
        rabbitTemplate.convertAndSend(delayExchange, delayRouting, stringBuffer.toString(), create());
    }

    /**
     * 死信接收队列
     */
    public void deadLetter(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("this message comes from dead letter Exchange,time:").append(LocalDateTime.now());
        rabbitTemplate.convertAndSend(dlxExchange, dlxRoutingKey, stringBuffer.toString(), create());
    }

    /**
     * 延时队列
     * @param time
     */
    public void dp(Integer time){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("this message comes from delay plugins Exchange,time:").append(LocalDateTime.now());
        rabbitTemplate.convertAndSend(dpExchange, dpRoutingKey, stringBuffer.toString(), a -> {
            a.getMessageProperties().setDelay(time);
            return a;
        }, create());
    }

    /**
     * 生成id
     */
    protected CorrelationData create() {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        return correlationData;
    }
}
