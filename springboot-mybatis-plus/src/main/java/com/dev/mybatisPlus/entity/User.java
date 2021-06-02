package com.dev.mybatisPlus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 用户管理
 * @author admin
 * @date 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
@ApiModel("用户管理")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
      * 用户标识
      */
    @ApiModelProperty(value = "用户标识",required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称",required = false)
    private String name;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄",required = false)
    private Integer age;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别",required = false)
    private Integer sex;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号",required = false)
    private String phone;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址",required = false)
    private String address;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日",required = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthday;

    @Override
    protected Serializable pkVal(){
        return this.id;
    }
}
