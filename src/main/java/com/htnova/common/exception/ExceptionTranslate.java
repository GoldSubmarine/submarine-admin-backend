package com.htnova.common.exception;

import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.Result;
import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ExceptionTranslate {

    private ExceptionTranslate() {}

    public static Result<?> translate(Throwable error) {
        Result<?> result;
        while (error instanceof ServletException && error.getCause() != null) {
            error = error.getCause();
        }
        // 这种方式针对RequestResponseBodyMethodProcessor 有效，也就是在 @Validated @RequestBody XXXDTO 的方式有效
        BindingResult bindingResult = extractBindingResult(error);
        if (bindingResult != null) {
            result =
                Result.build(
                    HttpStatus.BAD_REQUEST,
                    bindingResult.hasErrors() ? ResultStatus.BIND_ERROR : ResultStatus.NO_ERROR
                );
        } else {
            // 针对直接在controller里定义参数的情况 @NotNull String deviceId，需要直接捕获
            if (error instanceof ConstraintViolationException) {
                result = Result.build(HttpStatus.BAD_REQUEST, ResultStatus.BIND_ERROR);
            } else if (error instanceof ServiceException) {
                result = Result.build((ServiceException) error);
            } else {
                result = Result.build(HttpStatus.BAD_REQUEST, ResultStatus.SERVER_ERROR);
            }
        }
        return result;
    }

    private static BindingResult extractBindingResult(Throwable error) {
        if (error instanceof BindingResult) {
            return (BindingResult) error;
        }
        return error instanceof MethodArgumentNotValidException
            ? ((MethodArgumentNotValidException) error).getBindingResult()
            : null;
    }
}
