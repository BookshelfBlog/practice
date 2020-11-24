package com.dev.mybatisPlus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dev.mybatisPlus.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageHelper;
import com.dev.mybatisPlus.util.page.PageDomain;
import com.dev.mybatisPlus.util.page.TableDataInfo;
import java.util.Objects;
import com.dev.mybatisPlus.service.UserService;
import com.dev.mybatisPlus.entity.User;


/**
 *
 *  用户信息 前端控制器
 *
 * @author: hao niu
 * @date: 2020-11-18
 */
@RestController
@RequestMapping({"user"})
public class UserController extends BaseController {

    @Autowired
    private UserService service;

    @GetMapping({"list"})
    public TableDataInfo list(User entity,PageDomain pageDomain){
        PageHelper.startPage(CheckPageDomain(pageDomain));
        QueryWrapper<User> qw = new QueryWrapper<User>();
        qw.setEntity(entity).orderByAsc("id");
        return getDataTable(service.list(qw));
    }

    @GetMapping({"getOne/{id}"})
    public AjaxResult getById(@PathVariable("id") int id) {
        return success("查询成功!",service.getById(id));
    }

    @PostMapping({"save"})
    public AjaxResult save(@RequestBody User entity) {
        return super.toAjax(service.save(entity));
    }

    @PutMapping({"update"})
    public AjaxResult update(@RequestBody User entity) {
        if(Objects.isNull(entity.getId())){
            return error("参数为空!");
        }
        var result = service.getById(entity.getId());
        if(Objects.nonNull(result)){
            return super.toAjax(service.updateById(entity));
        }else{
            return error("数据不存在!");
        }
    }

    @DeleteMapping({"remove/{id}"})
    public AjaxResult remove(@PathVariable("id") int id) {
        var entity = service.getById(id);
        if(Objects.nonNull(entity)){
            return super.toAjax(service.removeById(id));
        }else{
            return error("数据不存在!");
        }
    }
}
