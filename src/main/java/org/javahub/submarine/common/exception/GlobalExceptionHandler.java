package org.javahub.submarine.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.javahub.submarine.common.constant.ResultCode;
import org.javahub.submarine.common.dto.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> handleException(Exception e){
        log.error("", e);
        return new ResponseEntity<>(Result.fail(ResultCode.BAD_REQUEST,"服务器异常，请稍后重试"), HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理 接口无权访问异常AccessDeniedException
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Result> handleAccessDeniedException(AccessDeniedException e){
        log.error("无权访问：{}", e.getMessage());
        return new ResponseEntity<>(Result.fail(ResultCode.UNAUTHORIZED, "无权访问"), HttpStatus.UNAUTHORIZED);
    }

    /**
     * 业务代码错误
     */
    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<Result> serviceException(ServiceException e) {
        log.error(e.getMessage());
        Result result = Result.fail();
        if(Objects.nonNull(e.getCode())) {
            result.setCode(e.getCode());
        }
        if(Objects.nonNull(e.getMessage())) {
            result.setMsg(e.getMessage());
        }
        if(Objects.nonNull(e.getHttpStatus())) {
            return new ResponseEntity<>(result, e.getHttpStatus());
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
