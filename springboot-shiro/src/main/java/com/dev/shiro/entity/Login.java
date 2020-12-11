package com.dev.shiro.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName : User  //类名
 * @Description : 用户实体类  //描述
 *
 * @Date: 2020-12-07 15:25  //时间
 */
@Data
public class Login implements Serializable {
    /**
     * 用户主键
     */
    private int id;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 角色
     */
    private int roleId;
    /**
     * 密码
     */
    private transient String password;
}
