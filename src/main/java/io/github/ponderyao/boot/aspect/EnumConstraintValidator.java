package io.github.ponderyao.boot.aspect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.github.ponderyao.boot.annotation.EnumValidate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * EnumConstraintValidator: 参数枚举校验
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class EnumConstraintValidator implements ConstraintValidator<EnumValidate, Object> {
    
    private Class<?> enumClass;
    
    private String targetField;
    
    private boolean nullable;

    @Override
    public void initialize(EnumValidate constraintAnnotation) {
        this.enumClass = constraintAnnotation.value();
        this.targetField = constraintAnnotation.field();
        this.nullable = constraintAnnotation.nullable();
    }
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return (nullable && Objects.isNull(value)) || (enumClass.isEnum() && fieldMatched(value));
    }
    
    private boolean fieldMatched(Object value) {
        return Arrays.stream(enumClass.getEnumConstants()).anyMatch(constant -> {
            try {
                Field field = enumClass.getDeclaredField(targetField);
                field.setAccessible(true);
                Object fieldValue = field.get(constant);
                if (fieldValue instanceof String) {
                    return StringUtils.equals((String) value, (String) fieldValue);
                }
                return Objects.equals(value, fieldValue);
            }
            catch (NoSuchFieldException e) {
                throw new RuntimeException(String.format("枚举类%s不存在字段%s", enumClass.getSimpleName(), targetField), e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(String.format("获取枚举类%s字段%s失败", enumClass.getSimpleName(), targetField), e);
            }
        });
    }
}
