package com.manage.seatManage.common;

/**
 * 全局错误码
 */
public enum ErrorCode {
    /**
     * description给前端使用
     */
    SUCCESS(0, "ok", "操作成功"),
    PARAMS_ERROR(40000, "请求参数错误", ""),
    PARAMS_NULL_ERROR(40001, "数据为空", ""),
    NO_LOGIN(40100, "未登录", ""),
    NO_AUTH(40101, "无权限", ""),
    FORBIDDEN(40301, "禁止访问", ""),
    SYSTEM_ERROR(50000,"系统内部异常",""),
    REGISTER_FAILED(40202,"注册失败","");

    private final int code;
    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述
     */
    private final String description;

     ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
