package com.dev.quartz.mapper;

import com.dev.quartz.entity.JobLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 定时任务调度日志表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-31
 */
public interface JobLogDao extends BaseMapper<JobLog> {
    void clear();
}
