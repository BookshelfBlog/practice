package com.dev.quartz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dev.quartz.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import java.util.Objects;
import com.dev.quartz.service.SysDictDataService;
import com.dev.quartz.entity.SysDictData;


/**
 *  字典数据表 前端控制器
 */
@Controller
@RequestMapping({"sysDictData"})
public class SysDictDataController {

    @Autowired
    private SysDictDataService service;

    @PostMapping({"list"})
    public AjaxResult list(SysDictData entity){
        QueryWrapper<SysDictData> qw = new QueryWrapper<SysDictData>();
        qw.setEntity(entity).orderByAsc("dict_code");
        return AjaxResult.success(service.list(qw));
    }

    @GetMapping({"selectOne"})
    public AjaxResult getById(@RequestParam("id") Integer id) {
        return AjaxResult.success("查询成功!",service.getById(id));
    }

    @PostMapping({"save"})
    public AjaxResult save(@RequestBody SysDictData entity) {
        return AjaxResult.toRow(service.save(entity));
    }

    @PutMapping({"update"})
    public AjaxResult update(@RequestBody SysDictData entity) {
        if(Objects.isNull(entity.getDictCode())){
            return AjaxResult.error("参数为空!");
        }
        var result = service.getById(entity.getDictCode());
        if(Objects.nonNull(result)){
            return AjaxResult.toRow(service.updateById(entity));
        }else{
            return AjaxResult.error("数据不存在!");
        }
    }

    @DeleteMapping({"remove"})
    public AjaxResult remove(@RequestParam("id") Integer id) {
        if(Objects.isNull(id)){
            return AjaxResult.error("参数为空!");
        }
        var entity = service.getById(id);
        if(Objects.nonNull(entity)){
            return AjaxResult.toRow(service.removeById(id));
        }else{
            return AjaxResult.error("数据不存在!");
        }
    }
}
