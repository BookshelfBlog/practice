package com.dev.amqp.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description : 模拟消费者  //描述
 */
@Component
@Slf4j
public class Receiver {

    @RabbitListener(queuesToDeclare = @Queue(value ="${amqp-config.direct.queue}",durable = "true"))
    public void direct(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        log.info("direct consumer:{}", new String(message.getBody()));
    }

    @RabbitListener(queuesToDeclare = @Queue(value = "${amqp-config.direct.queueB}",durable = "true"))
    public void directB(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        log.info("direct queueB consumer:{}", new String(message.getBody()));
    }

    @RabbitListener(queuesToDeclare = @Queue(value = "${amqp-config.fanout.one.queue}",durable = "true"))
    public void fanoutA(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        log.info("fanout consumer A:{}", new String(message.getBody()));
    }

    @RabbitListener(queuesToDeclare = @Queue(value = "${amqp-config.fanout.two.queue}",durable = "true"))
    public void fanoutB(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        log.info("fanout consumer B:{}", new String(message.getBody()));
    }

    @RabbitListener(queuesToDeclare = @Queue(value = "${amqp-config.topic.student.queue}",durable = "true"))
    public void studentQueue(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        log.info("topic student consumer:{}", new String(message.getBody()));
    }

    @RabbitListener(queuesToDeclare = @Queue(value = "${amqp-config.topic.teacher.queue}",durable = "true"))
    public void teacherQueue(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        log.info("topic teacher consumer:{}", new String(message.getBody()));
    }

    @RabbitListener(queuesToDeclare =
            @Queue(value = "${amqp-config.delay.delay}",durable = "true",arguments = {
                    @Argument(name = "x-dead-letter-exchange",value = "${amqp-config.direct.dlxExchange}"),
                    @Argument(name = "x-dead-letter-routing-key",value = "${amqp-config.direct.dlxRoutingKey}"),
                    @Argument(name = "x-message-ttl",value = "10000",type = "int"),
                    @Argument(name = "x-max-length",value = "5",type = "int")
            }
        )
    )
    public void delay(Message message, Channel channel) throws IOException {
        channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        log.info("direct delay consumer:{}", new String(message.getBody()));
    }

    @RabbitListener(queuesToDeclare = @Queue(value = "${amqp-config.delay.deadLetter}", durable = "true"))
    public void delayLetter(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("direct delay letter consumer:{}", new String(message.getBody()));
    }
}