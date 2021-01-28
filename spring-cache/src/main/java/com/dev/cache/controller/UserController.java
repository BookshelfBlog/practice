package com.dev.cache.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dev.cache.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;
import com.dev.cache.service.UserService;
import com.dev.cache.entity.User;


/**
 *
 *  用户信息 前端控制器
 *
 * @date: 2020-11-27
 */
@RestController
@RequestMapping({"user"})
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping({"list"})
    public AjaxResult list(User entity){
        return AjaxResult.success(service.list(entity));
    }

    @GetMapping({"/getOne/{id}"})
    public AjaxResult getById(@PathVariable("id") int id) {
        return AjaxResult.success("查询成功!",service.getById(id));
    }

    @PostMapping({"save"})
    public AjaxResult save(@RequestBody User entity) {
        return AjaxResult.toAjax(service.save(entity));
    }

    @PutMapping({"update"})
    public AjaxResult update(@RequestBody User entity) {
        if(Objects.isNull(entity.getId())){
            return AjaxResult.error("参数为空!");
        }
        var result = service.getById(entity.getId());
        if(Objects.nonNull(result)){
            return AjaxResult.toAjax(service.updateById(entity));
        }else{
            return AjaxResult.error("数据不存在!");
        }
    }

    @DeleteMapping({"/remove/{id}"})
    public AjaxResult remove(@PathVariable("id") int id) {
        var entity = service.getById(id);
        if(Objects.nonNull(entity)){
            return AjaxResult.toAjax(service.removeById(id));
        }else{
            return AjaxResult.error("数据不存在!");
        }
    }
}
