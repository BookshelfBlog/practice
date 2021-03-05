package com.dev.quartz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dev.quartz.entity.Job;
import com.dev.quartz.service.JobService;
import com.dev.quartz.util.AjaxResult;
import com.dev.quartz.util.ScheduleConstants;
import com.dev.quartz.util.TableDataInfo;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import java.util.Objects;


/**
 *  定时任务调度表 前端控制器
 */
@Controller
@RequestMapping({"job"})
public class JobController {

    private static String prefix = "";

    @Autowired
    private JobService service;

    @GetMapping({"/"})
    public String job(){
        return "job";
    }

    @GetMapping("/add")
    public String add(){
        return prefix + "add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Job byId = service.getById(id);
        model.addAttribute("job",byId);
        return prefix + "edit";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model){
        Job byId = service.getById(id);
        model.addAttribute("name","job");
        model.addAttribute("job",byId);
        return prefix + "detail";
    }

    @PostMapping({"list"})
    @ResponseBody
    public TableDataInfo list(Job entity, Page page){
        QueryWrapper<Job> qw = new QueryWrapper<Job>();
        qw.setEntity(entity).orderByAsc("job_id");
        return new TableDataInfo(service.page(page,qw),"查询成功!");
    }

    @GetMapping({"selectOne"})
    @ResponseBody
    public AjaxResult getById(@RequestParam("id") Integer id) {
        return AjaxResult.success("查询成功!",service.getById(id));
    }

    @PostMapping({"save"})
    @ResponseBody
    public AjaxResult save(Job entity) {
        return AjaxResult.toRow(service.save(entity));
    }

    @PostMapping({"run"})
    @ResponseBody
    public AjaxResult run(Job entity) {
        if (Objects.isNull(entity)) {
            return AjaxResult.error("参数为空!");
        }
        try {
            service.run(entity);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @PostMapping({"changeStatus"})
    @ResponseBody
    public AjaxResult changeStatus(Job entity) throws SchedulerException {
        if (Objects.equals(entity.getStatus(), ScheduleConstants.Status.PAUSE.getValue())) {
            return AjaxResult.toRow(service.pauseJob(entity));
        }
        if (Objects.equals(entity.getStatus(), ScheduleConstants.Status.NORMAL.getValue())){
            return AjaxResult.toRow(service.resumeJob(entity));
        }
        return AjaxResult.error("操作失败!");
    }

    @PutMapping({"update"})
    @ResponseBody
    public AjaxResult update(Job entity) {
        if(Objects.isNull(entity.getJobId())){
            return AjaxResult.error("参数为空!");
        }
        var result = service.getById(entity.getJobId());
        if(Objects.nonNull(result)){
            return AjaxResult.toRow(service.updateById(entity));
        }else{
            return AjaxResult.error("数据不存在!");
        }
    }

    @DeleteMapping({"remove"})
    @ResponseBody
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

    /**
     * 校验cron表达式是否有效
     */
    @PostMapping("/checkCronExpressionIsValid")
    @ResponseBody
    public boolean checkCronExpressionIsValid(Job job)
    {
        return CronExpression.isValidExpression(job.getCronExpression());
    }
}
