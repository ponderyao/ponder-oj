package io.github.poj.common;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Response: 统一响应对象
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
public class Response<T> implements Serializable {
    
    private static final long serialVersionUID = -5815572472419796131L;
    
    private int code;
    
    private String message;
    
    private T data;
    
    public Response(int code, T data) {
        this(code, ErrorCode.SUCCESS.getMessage(), data);
    }
    
    public Response(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage());
    }
    
    public Response(int code, String message) {
        this(code, message, null);
    }
    
    public static <T> Response<T> success(T data) {
        return new Response<>(ErrorCode.SUCCESS.getCode(), data);
    }
    
    public static <T> Response<T> success() {
        return success(null);
    }
    
    public static <T> Response<T> fail(ErrorCode errorCode) {
        return new Response<>(errorCode);
    }
    
    public static <T> Response<T> fail(int code, String message) {
        return new Response<>(code, message);
    }
    
    public static <T> Response<T> fail(ErrorCode errorCode, String message) {
        return new Response<>(errorCode.getCode(), message);
    }
    
}
