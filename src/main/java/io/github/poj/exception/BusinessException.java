package io.github.poj.exception;

import io.github.poj.common.ErrorCode;

/**
 * BusinessException: 业务异常
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {
    
    private static final long serialVersionUID = 1005180247876847057L;
    
    private final int code;
    
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
    
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
    
    public BusinessException(ErrorCode errorCode, String message) {
        this(errorCode.getCode(), message);
    }
    
    public int getCode() {
        return this.code;
    }
    
}
