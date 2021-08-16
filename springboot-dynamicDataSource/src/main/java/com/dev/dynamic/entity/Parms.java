package com.dev.dynamic.entity;

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
/**
 * 
 * @author admin
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("parms")
public class Parms extends Model<Parms> {

    private static final long serialVersionUID = 1L;

    /**
      * 自增主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 语言
     */
    private String languages;

    /**
     * 国家
     */
    private String nation;

    /**
     * 成立日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime established;

    @Override
    protected Serializable pkVal(){
        return this.id;
    }
}
