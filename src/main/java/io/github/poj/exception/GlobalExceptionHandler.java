package io.github.poj.exception;

import java.util.List;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.poj.common.ErrorCode;
import io.github.poj.common.Response;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

/**
 * GlobalExceptionHandler: 全局异常处理器
 *
 * @author PonderYao
 * @since 1.0.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ValidationException.class)
    public Response<?> validationExceptionHandler(ValidationException exception) {
        log.error(exception.getMessage(), exception);
        return Response.fail(ErrorCode.PARAMS_ERROR, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<?> validationExceptionHandler(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);
        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
        String message = allErrors.get(0).getDefaultMessage();
        return Response.fail(ErrorCode.PARAMS_ERROR, message);
    }
    
    @ExceptionHandler(BusinessException.class)
    public Response<?> businessExceptionHandler(BusinessException exception) {
        log.error(exception.getMessage(), exception);
        return Response.fail(exception.getCode(), exception.getMessage());
    }
    
    @ExceptionHandler(RuntimeException.class)
    public Response<?> runtimeExceptionHandler(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return Response.fail(ErrorCode.SYSTEM_ERROR);
    }
    
}
