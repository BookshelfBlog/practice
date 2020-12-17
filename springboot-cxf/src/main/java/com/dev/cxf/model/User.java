package com.dev.cxf.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName : User  //类名
 * @Description :   //描述
 * @Author :   //作者
 * @Date: 2020-12-14 16:48  //时间
 */
@Data
public class User implements Serializable {
    private String name;
    private String ip;

    public User() {
    }

    public User(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
