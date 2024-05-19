package com.manage.seatManage.exception;

import com.manage.seatManage.common.ErrorCode;

/**
 * 自定义异常类
 * 把所有的报错信息都集合起来
 * extends不用throw和try catch捕获
 * 扩充了俩个字段
 */
public class BusinessException extends RuntimeException {

    /**
     * 状态码信息
     */
    private final int code;
    /**
     * 状态码描述（详情）
     */
    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();

    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}



