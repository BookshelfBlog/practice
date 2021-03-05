package com.dev.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dev.quartz.entity.Job;
import org.quartz.SchedulerException;

/**
 * <p>
 * 定时任务调度表 服务类
 * </p>
 *
 * @author admin
 * @since 2021-01-31
 */
public interface JobService extends IService<Job> {
    void run(Job job) throws SchedulerException;
    boolean pauseJob(Job job) throws SchedulerException;
    boolean resumeJob(Job job) throws SchedulerException;
}
