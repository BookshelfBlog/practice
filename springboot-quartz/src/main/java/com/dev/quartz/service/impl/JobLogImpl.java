package com.dev.quartz.service.impl;

import com.dev.quartz.entity.JobLog;
import com.dev.quartz.mapper.JobLogDao;
import com.dev.quartz.service.JobLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 定时任务调度日志表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-31
 */
@Service
public class JobLogImpl extends ServiceImpl<JobLogDao, JobLog> implements JobLogService {

    @Override
    @Transactional
    public void clear() {
        getBaseMapper().clear();
    }
}
