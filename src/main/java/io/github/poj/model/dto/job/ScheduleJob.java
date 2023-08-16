package io.github.poj.model.dto.job;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * ScheduleJob: 定时任务
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Data
public class ScheduleJob {

    /**
     * 任务名称（方法）
     */
    @NotBlank(message = "任务名称不能为空")
    private String jobName;

    /**
     * 任务组（类）
     */
    @NotBlank(message = "任务组不能为空")
    private String JobGroup;

    /**
     * 任务说明
     */
    private String jobDesc;

    /**
     * 定时规则表达式
     */
    private String cron;

    /**
     * 定时任务状态
     */
    private String state;
    
}
