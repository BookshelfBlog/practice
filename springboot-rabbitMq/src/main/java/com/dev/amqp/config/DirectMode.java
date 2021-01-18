package com.dev.amqp.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description : 直连(默认)交换机 //描述
 */
@Configuration
public class DirectMode {
    @Value("${amqp-config.direct.queue}")
    private String QUEUE;
    @Value("${amqp-config.direct.queueB}")
    private String QUEUEB;
    @Value("${amqp-config.direct.exchange}")
    private String D_EXCHANGE;
    @Value("${amqp-config.direct.routingKey}")
    private String ROUTINGKEY;

    /**
     * AMQP default
     * 内部声明了一个默认交换机，根据队列名直接发送到指定队列
     * 监控插件关于default exchange描述：
     *  The default exchange is implicitly bound to every queue, with a routing key equal to the queue name.
     *  It is not possible to explicitly bind to, or unbind from the default exchange. It also cannot be deleted.
     * 翻译：
     *  默认交换机隐式绑定到每个队列，路由密钥等于队列名称。 无法从默认交换中显式绑定到或取消绑定。 它也无法删除。
     */
    @Bean
    Queue queue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    /**
     * 普通业务队列B
     */
    @Bean
    Queue queueB() {
        return QueueBuilder.durable(QUEUEB).build();
    }

    /**
     * 普通交换机
     */
    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(D_EXCHANGE,true,false);
    }

    @Bean
    Binding bindingB(){
        return BindingBuilder.bind(queueB()).to(directExchange()).with(ROUTINGKEY);
    }
}