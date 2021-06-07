package com.dev.mybatisPlus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dev.mybatisPlus.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.CollectionUtils;
import javax.validation.Valid;
import com.github.pagehelper.PageHelper;
import com.dev.mybatisPlus.util.page.PageDomain;
import com.dev.mybatisPlus.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Arrays;
import java.util.Objects;
import java.util.ArrayList;
import com.dev.mybatisPlus.service.UserService;
import com.dev.mybatisPlus.entity.User;

/**
 *
 *  用户管理 前端控制器
 *
 * @author: admin
 * @date: 2021-06-01
 */
@RestController
@RequestMapping({"user"})
@Api(tags = "用户管理管理")
public class UserController extends BaseController {

    @Autowired
    private UserService service;

    @ApiOperation(value = "用户管理列表查询")
    @GetMapping({"list"})
    public TableDataInfo list(User entity,PageDomain pageDomain){
        PageHelper.startPage(CheckPageDomain(pageDomain));
        QueryWrapper<User> qw = new QueryWrapper<User>();
        qw.setEntity(entity).orderByAsc("id");
        return getDataTable(service.list(qw));
    }

    @ApiOperation(value = "用户管理根据主键查询")
    @GetMapping({"/getOne/{id}"})
    public AjaxResult getById(@PathVariable("id") int id) {
        return success("查询成功!",service.getById(id));
    }

    @ApiOperation(value = "用户管理新增")
    @PostMapping({"save"})
    public AjaxResult save(@RequestBody @Valid User entity) {
        return super.toAjax(service.save(entity));
    }

    @ApiOperation(value = "用户管理批量新增")
    @PostMapping({"saveBatch"})
    public AjaxResult saveBatch(@RequestBody List<User> entitys) {
        if(CollectionUtils.isEmpty(entitys)){
            return error("参数为空!");
        }
        return super.toAjax(service.saveBatch(entitys));
    }

    @ApiOperation(value = "用户管理更新")
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

    @ApiOperation(value = "用户管理批量更新")
    @PutMapping({"updateBatch"})
    public AjaxResult updateBatch(@RequestBody List<User> entitys) {
        if(CollectionUtils.isEmpty(entitys)){
            return error("参数为空!");
        }
        return super.toAjax(service.updateBatchById(entitys));
    }

    @ApiOperation(value = "用户管理根据主键删除")
    @DeleteMapping({"/remove/{id}"})
    public AjaxResult remove(@PathVariable("id") int id) {
        var entity = service.getById(id);
        if(Objects.nonNull(entity)){
            return super.toAjax(service.removeById(id));
        }else{
            return error("数据不存在!");
        }
    }

    @ApiOperation(value = "用户管理批量根据主键删除")
    @DeleteMapping({"/remove/{ids}"})
    public AjaxResult removeAll(@PathVariable("ids") int[] ids) {
        if (Objects.isNull(ids) || ids.length < 1){
            return error("参数为空!");
        }
        return super.toAjax(service.removeByIds(new ArrayList<>(Arrays.asList(ids))));
    }
}
