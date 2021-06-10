package com.dev.docker.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dev.docker.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.CollectionUtils;
import javax.validation.Valid;
import java.util.Objects;
import java.util.List;
import com.dev.docker.service.InfoService;
import com.dev.docker.entity.Info;

import static com.dev.docker.util.AjaxResult.error;
import static com.dev.docker.util.AjaxResult.success;


/**
 *
 *  教师 前端控制器
 *
 * @author: admin
 * @date: 2021-06-10
 */
@RestController
@RequestMapping({"info"})
public class InfoController {

    @Autowired
    private InfoService service;

    @GetMapping({"list"})
    public AjaxResult list(Info entity,Page page){
        QueryWrapper<Info> qw = new QueryWrapper<Info>();
        qw.setEntity(entity).orderByAsc("id");
        return success(service.page(page,qw));
    }

    @GetMapping({"/getOne/{id}"})
    public AjaxResult getById(@PathVariable("id") int id) {
        return success("查询成功!",service.getById(id));
    }

    @PostMapping({"save"})
    public AjaxResult save(@RequestBody @Valid Info entity) {
        return service.save(entity) ? success() : error();
    }

    @PostMapping({"saveBatch"})
    public AjaxResult saveBatch(@RequestBody List<Info> entitys) {
        if(CollectionUtils.isEmpty(entitys)){
            return error("参数为空!");
        }
        return service.saveBatch(entitys) ? success() : error();
    }

    @PutMapping({"update"})
    public AjaxResult update(@RequestBody Info entity) {
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
    public AjaxResult updateBatch(@RequestBody List<Info> entitys) {
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
}
