package io.github.poj.exception;

import io.github.poj.common.ErrorCode;

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
