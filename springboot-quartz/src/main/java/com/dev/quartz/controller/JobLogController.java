package com.dev.quartz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dev.quartz.entity.Job;
import com.dev.quartz.service.JobService;
import com.dev.quartz.util.AjaxResult;
import com.dev.quartz.util.TableDataInfo;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import com.dev.quartz.service.JobLogService;
import com.dev.quartz.entity.JobLog;


/**
 *  定时任务调度日志表 前端控制器
 */
@Controller
@RequestMapping({"jobLog"})
public class JobLogController {

    @Autowired
    private JobLogService service;
    @Autowired
    private JobService jobService;

    private static String prifex = "";

    @GetMapping("/")
    public String jobLog(@RequestParam(value = "jobId",required = false) Integer jobId, Model model){
        if (jobId != null){
            Job byId = jobService.getById(jobId);
            model.addAttribute("job",byId);
        }
        return "jobLog";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model){
        if (id != null){
            JobLog byId = service.getById(id);
            model.addAttribute("name","jobLog");
            model.addAttribute("jobLog",byId);
        }
        return "detail";
    }

    @PostMapping({"list"})
    @ResponseBody
    public TableDataInfo list(JobLog entity, Page page){
        QueryWrapper<JobLog> qw = new QueryWrapper<JobLog>();
        qw.setEntity(entity).orderByAsc("job_log_id");
        return new TableDataInfo(service.page(page,qw),"查询成功!");
    }

    @GetMapping({"selectOne"})
    public AjaxResult getById(@RequestParam("id") Integer id) {
        return AjaxResult.success("查询成功!",service.getById(id));
    }

    @PostMapping({"save"})
    @ResponseBody
    public AjaxResult save(@RequestBody JobLog entity) {
        return AjaxResult.toRow(service.save(entity));
    }

    @PutMapping({"update"})
    @ResponseBody
    public AjaxResult update(@RequestBody JobLog entity) {
        if(Objects.isNull(entity.getJobLogId())){
            return AjaxResult.error("参数为空!");
        }
        var result = service.getById(entity.getJobLogId());
        if(Objects.nonNull(result)){
            return AjaxResult.toRow(service.updateById(entity));
        }else{
            return AjaxResult.error("数据不存在!");
        }
    }

    @DeleteMapping({"clean"})
    @ResponseBody
    public AjaxResult clean(){
        service.clear();
        return AjaxResult.success();
    }

    @DeleteMapping({"remove"})
    @ResponseBody
    public AjaxResult remove(@RequestParam("ids") String ids) {
        if(StringUtils.isBlank(ids)){
            return AjaxResult.error("参数为空!");
        }
        String[] split = ids.split(",");
        Integer[] id = (Integer[]) ConvertUtils.convert(split, Integer.class);
        List<Integer> lists = new ArrayList<>(id.length);
        CollectionUtils.addAll(lists, id);
        if(CollectionUtils.isNotEmpty(lists)){
            return AjaxResult.toRow(service.removeByIds(lists));
        }else{
            return AjaxResult.error("数据不存在!");
        }
    }
}
