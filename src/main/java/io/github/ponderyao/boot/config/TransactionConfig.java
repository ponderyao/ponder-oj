package io.github.ponderyao.boot.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.github.ponderyao.boot.config.properties.TransactionProperties;

/**
 * TransactionConfig: 全局事务配置
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Configuration
@ConditionalOnMissingBean(DataSourceTransactionManager.class)
@EnableConfigurationProperties(TransactionProperties.class)
@ConditionalOnProperty(prefix = TransactionProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class TransactionConfig {
    
    private final TransactionProperties properties;
    
    public TransactionConfig(TransactionProperties properties) {
        this.properties = properties;
    }

    @Bean
    @Primary
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 全局事务配置 Bean
     *
     * @param transactionManager 事务管理器
     * @return transactionAdvice
     */
    @Bean
    @ConditionalOnProperty(prefix = TransactionProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
    public TransactionInterceptor transactionAdvice(DataSourceTransactionManager transactionManager) {
        int propagation = properties.getPropagation();
        int timeout = properties.getTimeout();
        String[] readMethods = properties.getReadMethods();
        String[] writeMethods = properties.getWriteMethods();
        NameMatchTransactionAttributeSource transactionAttributeSource = new NameMatchTransactionAttributeSource();
        // 只读，不需要事务支撑
        RuleBasedTransactionAttribute readOnlyAttr = new RuleBasedTransactionAttribute();
        readOnlyAttr.setReadOnly(true);
        readOnlyAttr.setPropagationBehavior(propagation);
        // 包含写操作，需要事务支撑
        RuleBasedTransactionAttribute writeAttr = new RuleBasedTransactionAttribute();
        writeAttr.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        writeAttr.setPropagationBehavior(propagation);
        writeAttr.setTimeout(timeout);
        Map<String, TransactionAttribute> nameMap = assemblyNameMap(readMethods, writeMethods, readOnlyAttr, writeAttr);
        transactionAttributeSource.setNameMap(nameMap);
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(transactionManager);
        transactionInterceptor.setTransactionAttributeSource(transactionAttributeSource);
        return transactionInterceptor;
    }

    /**
     * 全局事务切面 Bean
     *
     * @param transactionAdvice 全局事务配置 Bean
     * @return transactionAdvisor
     */
    @Bean
    @ConditionalOnProperty(prefix = TransactionProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
    public Advisor transactionAdvisor(TransactionInterceptor transactionAdvice) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        String pointcutExpression = properties.getPointcutExpression();
        if (StringUtils.isBlank(pointcutExpression)) {
            pointcutExpression = TransactionProperties.DEFAULT_POINTCUT_EXPRESSION;
        }
        pointcut.setExpression(pointcutExpression);
        return new DefaultPointcutAdvisor(pointcut, transactionAdvice);
    }

    private Map<String, TransactionAttribute> assemblyNameMap(
        String[] readMethods, String[] writeMethods, RuleBasedTransactionAttribute readOnlyAttr, RuleBasedTransactionAttribute writeAttr) {
        Map<String, TransactionAttribute> nameMap = new HashMap<>();
        doMapper(nameMap, writeMethods, writeAttr);
        doMapper(nameMap, readMethods, readOnlyAttr);
        return nameMap;
    }

    private void doMapper(Map<String, TransactionAttribute> map, String[] methods, RuleBasedTransactionAttribute attr) {
        for (String method : methods) {
            String key = method.equals("*") ? method : method + "*";
            map.put(key, attr);
        }
    }
    
}
