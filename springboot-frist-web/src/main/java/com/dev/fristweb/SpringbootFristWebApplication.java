package com.dev.fristweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootFristWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootFristWebApplication.class, args);
    }

    @PostMapping("/hello")
    public String sayHello(){
        return "hello springboot!";
    }
}
