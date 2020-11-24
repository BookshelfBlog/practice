package com.dev.mybatis.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName : User  //类名
 * @Description : 用户管理  //描述
 * @Author : hao niu  //作者
 * @Date: 2020-11-17 09:41  //时间
 */
@Data
public class User implements Serializable {
    private int id;
    private String name;
    private int age;
    private int sex;
    private String phone;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthday;
}
