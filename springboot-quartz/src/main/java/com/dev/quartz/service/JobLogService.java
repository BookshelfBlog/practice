package com.dev.quartz.service;

import com.dev.quartz.entity.JobLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 定时任务调度日志表 服务类
 * </p>
 *
 * @author admin
 * @since 2021-01-31
 */
public interface JobLogService extends IService<JobLog> {
    void clear();
}
