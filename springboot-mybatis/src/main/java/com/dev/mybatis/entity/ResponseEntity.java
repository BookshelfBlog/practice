package com.dev.mybatis.entity;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName : ResponseEntity  //类名
 * @Description : 响应实体  //描述
 * @Author : hao niu  //作者
 * @Date: 2020-11-17 09:09  //时间
 */
@Data
public class ResponseEntity implements Serializable {

    private static String SUCCESS_MSG = "操作成功!";
    private static String ERROR_MSG = "操作失败!";

    private int status;
    private Object data;
    private String msg;

    public ResponseEntity() {
    }

    public ResponseEntity(int status, Object data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public static ResponseEntity success(int status, Object data, String msg) {
        return new ResponseEntity(status,data,msg);
    }

    public static ResponseEntity success(Object data, String msg) {
        return success(HttpStatus.OK.value(),data,msg);
    }

    public static ResponseEntity success(List<?> data, String msg) {
        return success(HttpStatus.OK.value(),data,msg);
    }

    public static ResponseEntity success() {
        return success(HttpStatus.OK.value(),null,SUCCESS_MSG);
    }

    public static ResponseEntity error(int status, Object data, String msg) {
        return new ResponseEntity(status,data,msg);
    }

    public static ResponseEntity error(Object data, String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(),data,msg);
    }

    public static ResponseEntity error(List<?> data, String msg) {
        return error(HttpStatus.OK.value(),data,msg);
    }
}
