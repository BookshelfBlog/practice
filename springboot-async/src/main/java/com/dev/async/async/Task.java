package com.dev.async.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @Async
 * 注解放在类上，该类所有方法异步执行
 * 注解放在方法上，该方法异步执行
 */
@Async
@Component
public class Task {

    public void run1(){
        System.out.println("任务一");
    }

    public void run2(){
        System.out.println("任务二");
    }

    public void run3(){
        System.out.println("任务三");
    }

    public CompletableFuture<String> run4(){
        System.out.println("任务四");
        return CompletableFuture.completedFuture("执行成功!");
    }

    public CompletableFuture<Integer> run5(){
        System.out.println("任务五");
        return CompletableFuture.completedFuture(0);
    }

    public CompletableFuture<Boolean> run6(){
        System.out.println("任务六");
        return CompletableFuture.completedFuture(true);
    }
}
