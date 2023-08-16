package io.github.ponderyao.boot.exception;

import io.github.ponderyao.boot.common.ErrorCode;

/**
 * ThrowUtils: 抛异常工具类
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ThrowUtils {
    
    public static void throwIf(boolean condition, RuntimeException exception) {
        if (condition) {
            throw exception;
        }
    }
    
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }
    
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }
    
}
