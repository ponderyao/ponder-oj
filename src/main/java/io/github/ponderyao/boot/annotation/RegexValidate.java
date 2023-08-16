package io.github.ponderyao.boot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.ponderyao.boot.aspect.RegexConstraintValidator;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * RegexValidate: 正则校验
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Documented
@Constraint(validatedBy = RegexConstraintValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RegexValidate {
    
    String regex();
    
    boolean nullable() default false;
    
    String message() default "数据格式不符合规范";

    Class<?>[] groups() default { };
    
    Class<? extends Payload>[] payload() default { };
    
}
