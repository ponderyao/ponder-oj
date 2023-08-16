package io.github.ponderyao.boot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import io.github.ponderyao.boot.common.ErrorCode;
import io.github.ponderyao.boot.constant.SymbolConstant;
import io.github.ponderyao.boot.exception.ThrowUtils;
import io.github.ponderyao.boot.job.ScheduleJobBean;
import io.github.ponderyao.boot.model.dto.job.ScheduleJob;
import io.github.ponderyao.boot.service.ScheduleService;
import io.github.ponderyao.boot.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ScheduleServiceImpl: 定时任务服务实现
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {
    
    private final Scheduler scheduler;
    
    @Override
    public void createJob(ScheduleJob scheduleJob) {
        JobKey jobKey = beforeProcessSchedule(scheduleJob, true);
        JobDetail jobDetail = JobBuilder.newJob(ScheduleJobBean.class)
            .withIdentity(jobKey)
            .withDescription(scheduleJob.getJobDesc())
            .build();
        CronTrigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup())
            .startNow()
            .withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCron()))
            .build();
        jobDetail.getJobDataMap().put(ScheduleJobBean.SCHEDULE_TASK, JsonUtils.toJson(scheduleJob));
        try {
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("创建定时任务【{}】失败：{}", scheduleJob.getJobGroup() + SymbolConstant.POINT + scheduleJob.getJobName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void pauseJob(ScheduleJob scheduleJob) {
        JobKey jobKey = beforeProcessSchedule(scheduleJob, false);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.error("暂停定时任务【{}】失败：{}", scheduleJob.getJobGroup() + SymbolConstant.POINT + scheduleJob.getJobName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void resumeJob(ScheduleJob scheduleJob) {
        JobKey jobKey = beforeProcessSchedule(scheduleJob, false);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            log.error("恢复定时任务【{}】失败：{}", scheduleJob.getJobGroup() + SymbolConstant.POINT + scheduleJob.getJobName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifyJob(ScheduleJob scheduleJob) {
        beforeProcessSchedule(scheduleJob, false);
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCron());
        try {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            trigger.getJobDataMap().put(ScheduleJobBean.SCHEDULE_TASK, JsonUtils.toJson(scheduleJob));
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            log.error("修改定时任务【{}】失败：{}", scheduleJob.getJobGroup() + SymbolConstant.POINT + scheduleJob.getJobName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeJob(ScheduleJob scheduleJob) {
        JobKey jobKey = beforeProcessSchedule(scheduleJob, false);
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        try {
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            log.error("删除定时任务【{}】失败：{}", scheduleJob.getJobGroup() + SymbolConstant.POINT + scheduleJob.getJobName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String queryJobState(ScheduleJob scheduleJob) {
        beforeProcessSchedule(scheduleJob, false);
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        try {
            Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
            return triggerState.name();
        } catch (SchedulerException e) {
            log.error("查询定时任务【{}】状态失败：{}", scheduleJob.getJobGroup() + SymbolConstant.POINT + scheduleJob.getJobName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ScheduleJob> queryJobs() {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        List<ScheduleJob> scheduleJobList = new ArrayList<>();
        try {
            for (JobKey jobKey : scheduler.getJobKeys(matcher)) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    ScheduleJob scheduleJob = new ScheduleJob();
                    scheduleJob.setJobName(jobKey.getName());
                    scheduleJob.setJobGroup(jobKey.getGroup());
                    scheduleJob.setJobDesc(jobDetail.getDescription());
                    scheduleJob.setCron(((CronTrigger) trigger).getCronExpression());
                    scheduleJob.setState(scheduler.getTriggerState(trigger.getKey()).name());
                    scheduleJobList.add(scheduleJob);
                }
            }
            return scheduleJobList;
        } catch (SchedulerException e) {
            log.error("查询所有定时任务失败：{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private boolean checkSchedule(JobKey jobKey) {
        try {
            return scheduler.checkExists(jobKey);
        } catch (SchedulerException e) {
            log.error("校验定时任务【{}】失败：{}", jobKey.getGroup() + SymbolConstant.POINT + jobKey.getName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    private JobKey beforeProcessSchedule(ScheduleJob scheduleJob, boolean checkExists) {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        boolean exists = checkSchedule(jobKey);
        ThrowUtils.throwIf(!checkExists && !exists, ErrorCode.NOT_FOUND_ERROR, "定时任务不存在");
        ThrowUtils.throwIf(checkExists && exists, ErrorCode.PARAMS_ERROR, "定时任务已存在，不可重复创建");
        return jobKey;
    }
    
}
