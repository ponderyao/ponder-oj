package io.github.poj.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.transaction.TransactionDefinition;

import lombok.Getter;
import lombok.Setter;

/**
 * TransactionProperties: 事务配置参数
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = TransactionProperties.PREFIX)
public class TransactionProperties {

    public static final String PREFIX = "ponder-boot.transaction";
    
    public static final String DEFAULT_POINTCUT_EXPRESSION = "execution (io.github.poj.controller..*.*(..))";

    private Boolean enabled = true;

    private int propagation = TransactionDefinition.PROPAGATION_REQUIRED;

    private String[] writeMethods = new String[]{};

    private String[] readMethods = new String[]{"*"};

    private int timeout = 50000;

    private String pointcutExpression = DEFAULT_POINTCUT_EXPRESSION;
    
}
