package com.htnova.common.dto;

import com.htnova.common.constant.ResultStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一的返回数据封装
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<E> {

    /**
     * 操作结果的状态码，200为成功，其余失败
     */
    private Integer code;

    /**
     * 操作结果的描述信息，可作为页面提示信息使用
     */
    private String msg;

    /**
     * 返回的业务数据
     */
    private E data;

    public static Result<Void> build(ResultStatus resultStatus) {
        return Result.<Void>builder().code(resultStatus.getCode()).msg(resultStatus.getMsg()).build();
    }

    /**
     * 用于接收前端拼接的 msg 数组
     */
    public static Result<Object> build(ResultStatus resultStatus, Object ...data) {
        return Result.<Object>builder().code(resultStatus.getCode()).msg(resultStatus.getMsg()).data(data).build();
    }

    /**
     * 用于接收成功的业务数据
     */
    public static <E> Result<E> build(ResultStatus resultStatus, E data) {
        return Result.<E>builder().code(resultStatus.getCode()).msg(resultStatus.getMsg()).data(data).build();
    }

    public static <E> Result<E> build(int code, String msg, E data) {
        return Result.<E>builder().code(code).msg(msg).data(data).build();
    }

}
