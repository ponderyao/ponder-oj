package io.github.ponderyao.boot.aspect;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.github.ponderyao.boot.annotation.RegexValidate;

/**
 * RegexConstraintValidator: 参数正则校验
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class RegexConstraintValidator implements ConstraintValidator<RegexValidate, String> {
    
    private String regex;
    
    private boolean nullable;

    @Override
    public void initialize(RegexValidate constraintAnnotation) {
        this.regex = constraintAnnotation.regex();
        this.nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (nullable && StringUtils.isBlank(value)) || (StringUtils.isNotBlank(value) && value.matches(regex));
    }
}
