package com.dev.docker.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 学生
 * @author admin
 * @date 2021-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Student extends Model<Student> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键标识
     */
    @TableId(value = "stu_id", type = IdType.AUTO)
    private Integer stuId;

    /**
     * 名称
     */
    private String stuName;

    /**
     * 编号
     */
    private String stuNo;

    /**
     * 班级
     */
    private Integer classId;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 入学时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insTime;

    /**
     * 最近修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime editTime;

    /**
     * 状态：1正常、2休学
     */
    private Integer status;

    /**
     * 删除标志：1正常、2删除
     */
    private Integer deleteFlag;

    @Override
    protected Serializable pkVal(){
        return this.stuId;
    }
}
