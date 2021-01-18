package com.dev.amqp.controller;

import com.dev.amqp.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description : 服务调用接口  //描述
 */
@RestController
public class Test {

    @Autowired
    Producer producer;

    @GetMapping("/direct")
    public void direct(){
        producer.direct();
    }

    @GetMapping("/directB")
    public void directB(){
        producer.directB();
    }

    @GetMapping("/fanout")
    public void fanout(){
        producer.fanout();
    }

    @GetMapping("/student")
    public void student(){
        producer.student();
    }

    @GetMapping("/teacher")
    public void teacher(){
        producer.teacher();
    }

    @GetMapping("/topic")
    public void topic(){
        producer.topic();
    }

    @GetMapping("/delay")
    public void delay(){
        producer.delay();
    }

    @GetMapping("/delayLetter")
    public void delayLetter(){
        producer.deadLetter();
    }
}
