package com.manage.seatManage.common;

/***
 * 返回工具类
 *
 */
public class ResultUtils {
    /**
     * 成功
     * @param data 数据
     * @param <T> 对应的数据对象类型
     * @return
     */
    public static<T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"ok");
    }

    /**
     * 失败
     * @param errorCode 错误码
     *
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }

    /**
     * 失败
     * @param errorCode 状态码
     * @param message 消息
     * @param description 具体描述
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String message,String description){
        return new BaseResponse(errorCode.getCode(),null,message,description);
    }

    /**
     * 失败
     * @param errorCode 状态码
     * @param description 描述
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String description){
        return new BaseResponse(errorCode.getCode(),errorCode.getMessage(),description);
    }
    /**
     * 失败
     *
     */
    public static BaseResponse error(int code,String message,String description){
        return new BaseResponse(code,null,message,description);
    }



}
