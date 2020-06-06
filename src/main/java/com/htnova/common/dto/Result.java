package com.htnova.common.dto;

import com.htnova.common.constant.ResultStatus;
import com.htnova.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/** 统一的返回数据封装 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<E> {

    private static final HttpStatus DEFAULT_STATUS = HttpStatus.OK;

    private Integer status;

    private Integer code;

    private String msg;

    private E data;

    public static Result<Void> build(ResultStatus resultStatus) {
        return Result.<Void>builder()
                .status(DEFAULT_STATUS.value())
                .code(resultStatus.getCode())
                .msg(resultStatus.getMsg())
                .build();
    }

    public static Result<Void> build(HttpStatus httpStatus, ResultStatus resultStatus) {
        return Result.<Void>builder()
                .status(httpStatus.value())
                .code(resultStatus.getCode())
                .msg(resultStatus.getMsg())
                .build();
    }

    public static Result<Object> build(ServiceException exception) {
        return Result.<Object>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(exception.getCode())
                .msg(exception.getMessage())
                .data(exception.getData())
                .build();
    }

    /** 用于接收前端拼接的 msg 数组 */
    public static Result<Object> build(
            HttpStatus httpStatus, ResultStatus resultStatus, Object... data) {
        return Result.<Object>builder()
                .status(httpStatus.value())
                .code(resultStatus.getCode())
                .msg(resultStatus.getMsg())
                .data(data)
                .build();
    }

    /** 用于接收前端拼接的 msg 数组 */
    public static Result<Object> build(ResultStatus resultStatus, Object... data) {
        return Result.<Object>builder()
                .status(DEFAULT_STATUS.value())
                .code(resultStatus.getCode())
                .msg(resultStatus.getMsg())
                .data(data)
                .build();
    }

    /** 用于接收成功的业务数据 */
    public static <E> Result<E> build(ResultStatus resultStatus, E data) {
        return Result.<E>builder()
                .status(DEFAULT_STATUS.value())
                .code(resultStatus.getCode())
                .msg(resultStatus.getMsg())
                .data(data)
                .build();
    }

    public static <E> Result<E> build(HttpStatus status, int code, String msg, E data) {
        return Result.<E>builder().status(status.value()).code(code).msg(msg).data(data).build();
    }
}
