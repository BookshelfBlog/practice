package com.dev.async;

import com.dev.async.async.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootApplication
@RestController
@EnableAsync
public class AsyncApplication {

    @Autowired
    private Task task;

    public static void main(String[] args) {
        SpringApplication.run(AsyncApplication.class, args);
    }

    @PostMapping("/run")
    public void index() throws ExecutionException, InterruptedException {
        task.run1();
        task.run2();
        task.run3();
        Future<String> stringFuture = task.run4();
        Future<Integer> integerFuture = task.run5();
        Future<Boolean> booleanFuture = task.run6();
        for (;;){
            if (stringFuture.isDone() && integerFuture.isDone() && booleanFuture.isDone()) {
                System.out.println(task.run4().get());
                System.out.println(task.run5().get());
                System.out.println(task.run6().get());
                break;
            }
        }
    }
}
