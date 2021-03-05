package com.dev.quartz.task;

import org.springframework.stereotype.Component;


/**
 * @ClassName : CustomizeJob  //类名
 * @Description :   //描述
 * @Author :   //作者
 * @Date: 2021-01-31 15:28  //时间
 */
@Component("TaskList")
public class TaskList {

    public void test(){
        System.out.println("hello!");
    }

    public void msg(String str){
        System.out.println(str);
    }
}
