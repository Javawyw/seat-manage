package com.manage.seatManage.exception;

import com.manage.seatManage.common.ErrorCode;
import com.manage.seatManage.common.ResultUtils;
import com.manage.seatManage.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse  businessExceptionHandler(BusinessException e){
        log.error("businessException",e.getMessage(),e);//提供log对象，只要出现runtimeException都会保存到日志中
        return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeException(RuntimeException e){
        log.error("runtimeException",e);//提供log对象，只要出现runtimeException都会保存到日志中
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }
}
