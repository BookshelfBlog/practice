package com.dev.quartz.util;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * @ClassName : CustomizeJobExecution  //类名
 * @Description : 任务执行器  //描述
 * @Author :   //作者
 * @Date: 2021-02-25 16:09  //时间
 */
@DisallowConcurrentExecution
public class CustomizeJobDisallowConcurrentExecution extends AbstractCustomizeJob {
    @Override
    protected void doExecute(JobExecutionContext jobExecutionContext, com.dev.quartz.entity.Job job) throws Exception {
        JobInvokeUtil.invokeMethod(job);
    }
}
