package com.htnova.common.exception;

import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.Result;
import javax.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ExceptionTranslate {

    private ExceptionTranslate() {}

    public static Result<?> translate(Throwable error) {
        Result<?> result;
        while (error instanceof ServletException && error.getCause() != null) {
            error = error.getCause();
        }
        BindingResult bindingResult = extractBindingResult(error);
        if (bindingResult != null) {
            result =
                    Result.build(
                            HttpStatus.BAD_REQUEST,
                            bindingResult.hasErrors()
                                    ? ResultStatus.BIND_ERROR
                                    : ResultStatus.NO_ERROR);
        } else {
            if (error instanceof AuthenticationException) {
                result = Result.build(HttpStatus.UNAUTHORIZED, ResultStatus.UNAUTHORIZED);
            } else if (error instanceof AccessDeniedException) {
                result = Result.build(HttpStatus.FORBIDDEN, ResultStatus.FORBIDDEN);
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
