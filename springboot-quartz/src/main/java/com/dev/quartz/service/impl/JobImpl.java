package com.dev.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dev.quartz.entity.Job;
import com.dev.quartz.mapper.JobDao;
import com.dev.quartz.service.JobService;
import com.dev.quartz.util.ScheduleConstants;
import com.dev.quartz.util.ScheduleUtils;
import com.dev.quartz.util.TaskException;
import lombok.SneakyThrows;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * <p>
 * 定时任务调度表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-31
 */
@Service
public class JobImpl extends ServiceImpl<JobDao, Job> implements JobService {

    @Autowired
    Scheduler scheduler;

    @SneakyThrows
    @Override
    @Transactional
    public boolean save(Job entity) {
        entity.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int insert = this.baseMapper.insert(entity);
        if (checkRow(insert)){
            ScheduleUtils.createScheduleJob(scheduler, entity);
        }
        return checkRow(insert);
    }

    @SneakyThrows
    @Override
    @Transactional
    public boolean updateById(Job entity) {
        int i = this.baseMapper.updateById(entity);
        if (checkRow(i)){
            updateSchedule(entity);
        }
        return checkRow(i);
    }

    @SneakyThrows
    @Override
    @Transactional
    public boolean removeById(Serializable id) {
        Job job = getById(id);
        if (Objects.isNull(job)){
            return false;
        }
        int i = getBaseMapper().deleteById(job.getJobId());
        JobKey jobKey = ScheduleUtils.getJobKey(Long.valueOf(job.getJobId()), job.getJobGroup());
        if (checkRow(i) && scheduler.checkExists(jobKey)){
            scheduler.deleteJob(jobKey);
        }
        return checkRow(i);
    }

    @Override
    @Transactional
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        int num = 0;
        for (Serializable id:idList) {
            boolean b = removeById(id);
            if (b) {
                num++;
            }
        }
        return num == idList.size();
    }

    @Override
    public void run(Job job) throws SchedulerException {
        Job byId = getById(job.getJobId());
        if (Objects.nonNull(byId)){
            JobKey jobKey = ScheduleUtils.getJobKey(Long.valueOf(byId.getJobId()), byId.getJobGroup());
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put(ScheduleConstants.TASK_PROPERTIES, byId);
            scheduler.triggerJob(jobKey, jobDataMap);
        }
    }

    @Override
    public boolean pauseJob(Job job) throws SchedulerException {
        Job byId = getById(job.getJobId());
        byId.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        boolean b = updateById(byId);
        if (b){
            JobKey jobKey = ScheduleUtils.getJobKey(Long.valueOf(byId.getJobId()), byId.getJobGroup());
            scheduler.pauseJob(jobKey);
        }
        return b;
    }

    @Override
    public boolean resumeJob(Job job) throws SchedulerException {
        Job byId = getById(job.getJobId());
        byId.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        boolean b = updateById(byId);
        if (b){
            JobKey jobKey = ScheduleUtils.getJobKey(Long.valueOf(byId.getJobId()), byId.getJobGroup());
            scheduler.resumeJob(jobKey);
        }
        return b;
    }


    protected boolean checkRow(int row){
        return row > 0 ? true : false;
    }

    private void updateSchedule(Job job) throws SchedulerException, TaskException {
        JobKey jobKey = ScheduleUtils.getJobKey(Long.valueOf(job.getJobId()), job.getJobGroup());
        if (scheduler.checkExists(jobKey)){
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler,job);
    }
}
