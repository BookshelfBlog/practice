package com.dev.mybatis.controller;

import com.dev.mybatis.entity.ResponseEntity;
import com.dev.mybatis.entity.User;
import com.dev.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @ClassName : UserController  //类名
 * @Description : 用户管理  //描述
 * @Author : hao niu //作者
 * @Date: 2020-11-17 09:03  //时间
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseEntity getUserList(User user){
        return ResponseEntity.success(userService.getUserList(user),"操作成功!");
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity getUserById(@PathVariable("id") int id){
        return ResponseEntity.success(userService.getUserById(id),"操作成功!");
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody User user){
        return ResponseEntity.success(userService.insertUserSelective(user),"操作成功!");
    }

    @PutMapping("/edit")
    public ResponseEntity UserEdit(@RequestBody User user){
        if (Objects.isNull(user.getId())){
            return ResponseEntity.error(null,"参数为空!");
        }
        return ResponseEntity.success(userService.updateUserSelective(user),"操作成功!");
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity removeById(@PathVariable("id") int id){
        if (userService.removeById(id)){
            return ResponseEntity.success(null,"操作成功!");
        }else {
            return ResponseEntity.error(null,"操作失败!");
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity removeByUser(@RequestBody User user){
        if (userService.removeBySelective(user)){
            return ResponseEntity.success(null,"操作成功!");
        }else {
            return ResponseEntity.error(null,"操作失败!");
        }
    }
}
