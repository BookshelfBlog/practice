package com.dev.quartz.util;

import com.dev.quartz.entity.JobLog;
import com.dev.quartz.service.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


@Slf4j
public abstract class AbstractCustomizeJob implements Job {

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    private static ThreadLocal<LocalDateTime> threadLocal = new ThreadLocal<LocalDateTime>();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        com.dev.quartz.entity.Job job = new com.dev.quartz.entity.Job();
        BeanUtils.copyProperties(jobExecutionContext.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES),job);
        try {
            before(jobExecutionContext, job);
            if (Objects.nonNull(job)){
                doExecute(jobExecutionContext, job);
            }
            after(jobExecutionContext, job, null);
        }catch (Exception e){
            log.error("任务执行异常:{}", e);
            after(jobExecutionContext, job, e);
        }
    }

    private void before(JobExecutionContext jec, com.dev.quartz.entity.Job job){
        threadLocal.set(LocalDateTime.now());
    }

    private void after(JobExecutionContext jec, com.dev.quartz.entity.Job job, Exception e){
        LocalDateTime localDateTime = threadLocal.get();
        threadLocal.remove();
        JobLog jobLog = new JobLog();
        jobLog.setJobLogId(job.getJobId());
        jobLog.setJobName(job.getJobName());
        jobLog.setJobGroup(job.getJobGroup());
        jobLog.setInvokeTarget(job.getInvokeTarget());
        long time = ChronoUnit.SECONDS.between(LocalDateTime.now(), localDateTime);
        jobLog.setJobMessage(job.getRemark()+ " 总共耗时：" + time + "毫秒");
        if (Objects.nonNull(e)) {
            jobLog.setStatus(AbstractCustomizeJob.FAIL);
            jobLog.setExceptionInfo(substring(e.getMessage(), 0, 2000));
        } else {
            jobLog.setStatus(AbstractCustomizeJob.SUCCESS);
        }
        SpringUtils.getBean(JobLogService.class).save(jobLog);
    }

    protected abstract void doExecute(JobExecutionContext jobExecutionContext, com.dev.quartz.entity.Job job) throws Exception;

    /**
     * 截取字符串
     *
     * @param str 字符串
     * @param start 开始
     * @param end 结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end)
    {
        if (str == null)
        {
            return "";
        }

        if (end < 0)
        {
            end = str.length() + end;
        }
        if (start < 0)
        {
            start = str.length() + start;
        }

        if (end > str.length())
        {
            end = str.length();
        }

        if (start > end)
        {
            return "";
        }

        if (start < 0)
        {
            start = 0;
        }
        if (end < 0)
        {
            end = 0;
        }

        return str.substring(start, end);
    }
}
