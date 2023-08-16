package io.github.ponderyao.boot.config;

import java.io.IOException;
import java.util.Properties;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * ScheduleConfig: 定时任务配置
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Configuration
public class ScheduleConfig {
    
    private final DataSource dataSource;
    
    @Autowired
    public ScheduleConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }

    /**
     * 通过配置文件读取对quartz参数
     */
    @Bean
    public Properties properties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        // 对quartz.properties文件进行读取
        ClassPathResource classPathResource = new ClassPathResource("quartz.properties");
        propertiesFactoryBean.setLocation(classPathResource);
        // 在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setSchedulerName("TestScheduler");
        schedulerFactoryBean.setQuartzProperties(properties());
        // 延时启动
        schedulerFactoryBean.setStartupDelay(30);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
        return schedulerFactoryBean;
    }
    
}
