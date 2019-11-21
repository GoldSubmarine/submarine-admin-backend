package com.htnova.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 返回的业务状态码，非http状态码，
 * 20000为成功
 * 50000为错误
 * 如果要国际化，前端自身维护code对应的提示语，后端的 msg 不在前端显示，仅 debug 用
 * 如果不要国际化，前端直接展示后端的 msg 字段
 */
@ToString
@Getter
@AllArgsConstructor
public enum ResultStatus {

    //================success================
    SAVE_SUCCESS(20000, "保存成功"),
    DELETE_SUCCESS(20001, "删除成功"),
    LOGOUT_SUCCESS(20002, "成功退出登录"),


    NOT_FOUND(40004, "接口不存在"),

    //================fail================
    SERVER_ERROR(50000, "请求异常，请稍后重试"),
    INVALID_TOKEN(50001, "无效的token,请重新登录"),
    UNAUTHORIZED(50002, "未授权的访问"),
    BIND_ERROR(50003, "参数错误"),
    FORMAT_ERROR(50004, "格式化错误"),
    USER_NOT_EXIST(50005, "用户不存在"),
    ACCOUNT_FREEZE(50006, "当前账号已冻结，请联系管理员"),
    PASSWORD_WRONG(50007, "密码错误"),
    OLD_PASSWORD_WRONG(50008, "原密码错误"),
    NO_ERROR(50009, "No errors"),
    TO_LOGIN(50014, "重新登录");


    /**
     * 所有成功或失败的提示都要定义code
     */
    private int code;

    /**
     * 可能会有占位符，前端根据返回的 Result.data 字段，进行拼接
     */
    private final String msg;
}
