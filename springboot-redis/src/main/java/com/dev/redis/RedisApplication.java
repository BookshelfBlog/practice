package com.dev.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@SpringBootApplication
@RestController
public class RedisApplication {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

    @GetMapping("/get/{key}")
    public Object getKey(@PathVariable("key") String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    @PostMapping("/set")
    public boolean setKey(String key, String v){
        stringRedisTemplate.opsForValue().set(key,v);
        return stringRedisTemplate.hasKey(key);
    }
}
