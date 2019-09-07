package org.javahub.submarine.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一的返回数据封装
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<E> implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 801303944859566772L;

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

    public static <E> Result<E> success() {
        return Result.<E>builder().code(200).msg("调用成功").build();
    }

    public static <E> Result<E> successMsg(String msg) {
        return Result.<E>builder().code(200).msg(msg).build();
    }

    public static <E> Result<E> success(E data) {
        return Result.<E>builder().code(200).msg("调用成功").data(data).build();
    }

    public static <E> Result<E> success(String msg, E data) {
        return Result.<E>builder().code(200).msg(msg).data(data).build();
    }

    public static <E> Result<?> success(int code, E data) {
        return Result.<E>builder().data(data).code(code).msg("调用成功").build();
    }

    public static <E> Result<?> success(int code, String msg, E data) {
        return Result.<E>builder().data(data).code(code).msg(msg).build();
    }

    public static <E> Result<E> fail() {
        return Result.<E>builder().code(0).msg("调用失败").build();
    }

    public static <E> Result<E> failMsg(String msg) {
        return Result.<E>builder().code(0).msg(msg).build();
    }

    public static <E> Result<E> fail(E data) {
        return Result.<E>builder().code(0).msg("调用失败!").data(data).build();
    }

    public static <E> Result<?> fail(int code, String msg) {
        return Result.<E>builder().code(code).msg(msg).build();
    }

    public static <E> Result<?> fail(int code, String msg, E data) {
        return Result.<E>builder().data(data).code(code).msg(msg).build();
    }

    @Override
    public String toString() {
        return "Result{" + "code=" + code + ", msg='" + msg + '\'' + ", data=" + data + '}';
    }
}
