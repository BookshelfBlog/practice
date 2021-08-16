package com.dev.dynamic.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dev.dynamic.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.CollectionUtils;
import javax.validation.Valid;

import java.util.List;
import java.util.Arrays;
import java.util.Objects;
import java.util.ArrayList;
import com.dev.dynamic.service.ParmsService;
import com.dev.dynamic.entity.Parms;

import static com.dev.dynamic.util.AjaxResult.error;
import static com.dev.dynamic.util.AjaxResult.success;


/**
 *
 *   前端控制器
 *
 * @author: admin
 */
@RestController
@RequestMapping({"parms"})
public class ParmsController {

    @Autowired
    private ParmsService service;

    @GetMapping({"list"})
    public AjaxResult list(Parms entity){
        QueryWrapper<Parms> qw = new QueryWrapper<Parms>();
        qw.setEntity(entity).orderByAsc("id");
        return success(service.list(qw));
    }

    @GetMapping({"page"})
    public AjaxResult page(Parms entity,Page page){
        QueryWrapper<Parms> qw = new QueryWrapper<Parms>();
        qw.setEntity(entity).orderByAsc("id");
        return success(service.page(page,qw));
    }

    @GetMapping({"/getOne/{id}"})
    public AjaxResult getById(@PathVariable("id") int id) {
        return success("查询成功!",service.getById(id));
    }

    @PostMapping({"save"})
    public AjaxResult save(@RequestBody @Valid Parms entity) {
        return service.save(entity) ? success() : error();
    }

    @PostMapping({"saveBatch"})
    public AjaxResult saveBatch(@RequestBody List<Parms> entitys) {
        if(CollectionUtils.isEmpty(entitys)){
            return error("参数为空!");
        }
        return service.saveBatch(entitys) ? success() : error();
    }

    @PutMapping({"update"})
    public AjaxResult update(@RequestBody Parms entity) {
        if(Objects.isNull(entity.getId())){
            return error("参数为空!");
        }
        var result = service.getById(entity.getId());
        if(Objects.nonNull(result)){
            return service.updateById(entity) ? success() : error();
        }else{
            return error("数据不存在!");
        }
    }

    @PutMapping({"updateBatch"})
    public AjaxResult updateBatch(@RequestBody List<Parms> entitys) {
        if(CollectionUtils.isEmpty(entitys)){
            return error("参数为空!");
        }
        return service.updateBatchById(entitys) ? success() : error();
    }

    @DeleteMapping({"/remove/{id}"})
    public AjaxResult remove(@PathVariable("id") int id) {
        var entity = service.getById(id);
        if(Objects.nonNull(entity)){
            return service.removeById(id) ? success() : error();
        }else{
            return error("数据不存在!");
        }
    }

    @DeleteMapping({"/remove/{ids}"})
    public AjaxResult removeAll(@PathVariable("ids") int[] ids) {
        if (Objects.isNull(ids) || ids.length < 1){
            return error("参数为空!");
        }
        return service.removeByIds(new ArrayList<>(Arrays.asList(ids))) ? success() : error();
    }
}
