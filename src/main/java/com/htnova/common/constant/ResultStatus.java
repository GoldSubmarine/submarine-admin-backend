package com.htnova.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 返回的业务状态码，非http状态码， 20000为成功 50000为错误 如果要国际化，前端自身维护code对应的提示语，后端的 msg 不在前端显示，仅 debug 用
 * 如果不要国际化，前端直接展示后端的 msg 字段
 */
@ToString
@Getter
@AllArgsConstructor
public enum ResultStatus {
    // ===============全局状态(10)=============

    SAVE_SUCCESS(10000, "保存成功"),
    EDIT_SUCCESS(10001, "修改成功"),
    DELETE_SUCCESS(10002, "删除成功"),
    NOT_FOUND(10003, "接口不存在"),
    SERVER_ERROR(10004, "请求异常，请稍后重试"),
    BIND_ERROR(10005, "参数错误"),
    FORMAT_ERROR(10006, "格式化错误"),
    NO_ERROR(10007, "No errors"),

    // ===============认证授权相关(11)=============

    LOGIN_SUCCESS(11000, "登录成功"),
    LOGIN_ERROR(11001, "登录失败"),
    LOGOUT_SUCCESS(11002, "退出成功"),
    LOGOUT_ERROR(11003, "退出失败"),
    USER_NOT_EXIST(11004, "用户不存在"),
    USERNAME_PASSWORD_ERROR(11005, "用户名密码错误"),
    PASSWORD_WRONG(11006, "密码错误"),
    ACCOUNT_FREEZE(11007, "当前账号已冻结，请联系管理员"),
    OLD_PASSWORD_WRONG(11008, "原密码错误"),
    UNAUTHORIZED(11009, "未认证的访问"),
    FORBIDDEN(11010, "未授权的访问"),

    // ===============接口签名相关(12)=============

    SIGN_MISS_ARG(12000, "签名参数不全"),
    SIGN_TIME_ERROR(12001, "签名时间戳过期"),
    SIGN_ERROR(12002, "签名错误"),
    SIGN_UPDATE(12003, "签名已更新"),

    // ===============文件管理相关(13)=============

    EMPTY_FILE(13000, "文件为空"),
    FILE_NOT_FOUND(13001, "文件不存在"),
    UPLOAD_FAILED(13002, "文件上传失败"),

    // ===============定时任务相关(14)=============
    QUARTZ_CREATE_FAIL(14000, "创建定时任务失败"),
    QUARTZ_PAUSE_FAIL(14001, "暂停定时任务失败"),
    QUARTZ_PAUSE_SUCCESS(14002, "停用定时任务成功"),
    QUARTZ_RESUME_FAIL(14003, "恢复定时任务失败"),
    QUARTZ_RUN_FAIL(14004, "运行定时任务出错"),
    QUARTZ_RUN_SUCCESS(14005, "运行定时任务成功"),
    QUARTZ_DELETE_FAIL(14006, "删除定时任务出错"),
    QUARTZ_EXPRESSION_INVALID(14007, "cron表达式错误"),
    QUARTZ_METHOD_NOT_EXIST(14008, "当前方法不存在"),
    QUARTZ_JOB_NAME_IS_EXIST(14009, "当前任务名已存在，不能重复");

    // ===============业务相关(99)=============

    /** 所有成功或失败的提示都要定义code */
    private final int code;

    /** 可能会有占位符，前端根据返回的 Result.data 字段，进行拼接 */
    private final String msg;
}
