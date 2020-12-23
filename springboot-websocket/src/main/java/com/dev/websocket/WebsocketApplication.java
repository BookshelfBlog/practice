package com.dev.websocket;

import com.dev.websocket.config.SendObjectMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@Controller
@EnableScheduling
public class WebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketApplication.class, args);
    }

    private static List<String> list = Arrays.asList("有内鬼，终止交易!","年轻人，不讲武德，我劝你耗子尾汁!","加油，干饭人!","早安，打工人!");

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/message")
    public String message(){
        return "message";
    }

    @Scheduled(cron = "0/10 * * * * ?")
    private void taskRun(){
        Random random = new Random();
        SendObjectMessage.sendInfo(list.get(random.nextInt(list.size())));
    }
}
