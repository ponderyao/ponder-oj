package io.github.ponderyao.boot.controller;

import java.util.List;

import org.quartz.CronExpression;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.ponderyao.boot.common.ErrorCode;
import io.github.ponderyao.boot.common.Response;
import io.github.ponderyao.boot.exception.ThrowUtils;
import io.github.ponderyao.boot.model.dto.job.ScheduleJob;
import io.github.ponderyao.boot.service.ScheduleService;
import lombok.RequiredArgsConstructor;

/**
 * ScheduleController: 定时任务接口
 *
 * @author PonderYao
 * @since 1.0.0
 */
@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class ScheduleController {
    
    private final ScheduleService scheduleService;

    /**
     * 创建定时任务
     */
    @PostMapping("/create")
    public Response<Boolean> createJob(@Validated @RequestBody ScheduleJob scheduleJob) {
        boolean offerCron = CronExpression.isValidExpression(scheduleJob.getCron());
        ThrowUtils.throwIf(!offerCron, ErrorCode.PARAMS_ERROR, "cron表达式无效或未提供");
        scheduleService.createJob(scheduleJob);
        return Response.success(true);
    }

    /**
     * 暂停定时任务
     */
    @PostMapping("/pause")
    public Response<Boolean> pauseJob(@Validated @RequestBody ScheduleJob scheduleJob) {
        scheduleService.pauseJob(scheduleJob);
        return Response.success(true);
    }

    /**
     * 恢复定时任务
     */
    @PostMapping("/resume")
    public Response<Boolean> resumeJob(@Validated @RequestBody ScheduleJob scheduleJob) {
        scheduleService.resumeJob(scheduleJob);
        return Response.success(true);
    }

    /**
     * 修改定时任务
     */
    @PostMapping("/modify")
    public Response<Boolean> modifyJob(@Validated @RequestBody ScheduleJob scheduleJob) {
        boolean offerCron = CronExpression.isValidExpression(scheduleJob.getCron());
        ThrowUtils.throwIf(!offerCron, ErrorCode.PARAMS_ERROR, "cron表达式无效或未提供");
        scheduleService.modifyJob(scheduleJob);
        return Response.success(true);
    }

    /**
     * 删除定时任务
     */
    @PostMapping("/remove")
    public Response<Boolean> removeJob(@Validated @RequestBody ScheduleJob scheduleJob) {
        scheduleService.removeJob(scheduleJob);
        return Response.success(true);
    }

    /**
     * 查询定时任务状态
     */
    @PostMapping("/state")
    public Response<String> queryJobState(@Validated @RequestBody ScheduleJob scheduleJob) {
        String state = scheduleService.queryJobState(scheduleJob);
        return Response.success(state);
    }

    /**
     * 查询所有定时任务
     */
    @GetMapping("")
    public Response<List<ScheduleJob>> queryJobs() {
        List<ScheduleJob> scheduleJobList = scheduleService.queryJobs();
        return Response.success(scheduleJobList);
    }
    
}
