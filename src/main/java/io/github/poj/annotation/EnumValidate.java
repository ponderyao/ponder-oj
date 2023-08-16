package io.github.poj.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.poj.aspect.EnumConstraintValidator;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * EnumValidate: 枚举校验
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Documented
@Constraint(validatedBy = EnumConstraintValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValidate {
    
    Class<?> value();
    
    String field() default "code";

    boolean nullable() default false;

    String message() default "参数内容不符合规范";

    Class<?>[] groups() default { };
    
    Class<? extends Payload>[] payload() default { };
    
}
