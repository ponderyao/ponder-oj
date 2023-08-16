package io.github.ponderyao.boot.service;

import java.util.List;

import io.github.ponderyao.boot.model.dto.job.ScheduleJob;

/**
 * ScheduleService: 定时任务服务
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface ScheduleService {

    /**
     * 创建定时任务
     * 
     * @param scheduleJob 定时任务
     */
    void createJob(ScheduleJob scheduleJob);

    /**
     * 暂停定时任务
     *
     * @param scheduleJob 定时任务
     */
    void pauseJob(ScheduleJob scheduleJob);

    /**
     * 恢复定时任务
     *
     * @param scheduleJob 定时任务
     */
    void resumeJob(ScheduleJob scheduleJob);

    /**
     * 修改定时任务
     *
     * @param scheduleJob 定时任务
     */
    void modifyJob(ScheduleJob scheduleJob);

    /**
     * 删除定时任务
     *
     * @param scheduleJob 定时任务
     */
    void removeJob(ScheduleJob scheduleJob);

    /**
     * 查询定时任务状态
     * 
     * @param scheduleJob 定时任务
     * @return 定时任务状态
     */
    String queryJobState(ScheduleJob scheduleJob);

    /**
     * 查询所有定时任务
     * 
     * @return 定时任务列表
     */
    List<ScheduleJob> queryJobs();
    
}
