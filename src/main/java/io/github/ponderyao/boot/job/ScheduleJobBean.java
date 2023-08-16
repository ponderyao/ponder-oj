package io.github.ponderyao.boot.job;

import java.lang.reflect.Method;
import java.util.Objects;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.ReflectionUtils;

import io.github.ponderyao.boot.model.dto.job.ScheduleJob;
import io.github.ponderyao.boot.utils.JsonUtils;
import io.github.ponderyao.boot.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ScheduleJobBean: 任务创建类
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Slf4j
public class ScheduleJobBean extends QuartzJobBean {
    
    public static final String SCHEDULE_TASK = "ScheduleTask";
    
    @Override
    protected void executeInternal(JobExecutionContext context) {
        log.info("开始执行定时任务");
        String jobJson = context.getMergedJobDataMap().getString(SCHEDULE_TASK);
        ScheduleJob scheduleJob = JsonUtils.fromJson(jobJson, ScheduleJob.class);
        if (Objects.isNull(scheduleJob)) {
            log.error("解析定时任务失败，定时任务信息：{}", jobJson);
            throw new RuntimeException("解析定时任务失败");
        }
        log.info("正在执行定时任务【{}】", scheduleJob.getJobName());
        try {
            Object jobGroupBean = SpringContextUtils.getBean(scheduleJob.getJobGroup());
            Method jobMethod = jobGroupBean.getClass().getDeclaredMethod(scheduleJob.getJobName());
            ReflectionUtils.makeAccessible(jobMethod);
            jobMethod.invoke(jobGroupBean);
        } catch (Exception e) {
            log.error("执行定时任务失败：{}", e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("定时任务结束");
    }
    
}
