package com.dev.quartz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dev.quartz.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import java.util.Objects;
import com.dev.quartz.service.SysDictTypeService;
import com.dev.quartz.entity.SysDictType;


/**
 *  字典类型表 前端控制器
 */
@Controller
@RequestMapping({"sysDictType"})
public class SysDictTypeController {

    @Autowired
    private SysDictTypeService service;

    @PostMapping({"list"})
    public AjaxResult list(SysDictType entity){
        QueryWrapper<SysDictType> qw = new QueryWrapper<SysDictType>();
        qw.setEntity(entity).orderByAsc("dict_id");
        return AjaxResult.success(service.list(qw));
    }

    @GetMapping({"selectOne"})
    public AjaxResult getById(@RequestParam("id") Integer id) {
        return AjaxResult.success("查询成功!",service.getById(id));
    }

    @PostMapping({"save"})
    public AjaxResult save(@RequestBody SysDictType entity) {
        return AjaxResult.toRow(service.save(entity));
    }

    @PutMapping({"update"})
    public AjaxResult update(@RequestBody SysDictType entity) {
        if(Objects.isNull(entity.getDictId())){
            return AjaxResult.error("参数为空!");
        }
        var result = service.getById(entity.getDictId());
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
