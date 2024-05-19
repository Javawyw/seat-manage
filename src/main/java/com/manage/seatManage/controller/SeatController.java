package com.manage.seatManage.controller;

import com.manage.seatManage.Constant.UserConstant;
import com.manage.seatManage.common.BaseResponse;
import com.manage.seatManage.common.ErrorCode;
import com.manage.seatManage.common.ResultUtils;
import com.manage.seatManage.exception.BusinessException;
import com.manage.seatManage.model.domain.Seat;
import com.manage.seatManage.model.domain.User;
import com.manage.seatManage.service.SeatService;
import com.manage.seatManage.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/seat")
@CrossOrigin
@Slf4j
public class SeatController implements UserConstant {
    @Resource
    private SeatService seatService;

    @Resource
    private UserService userService;


    @PostMapping("/add")
    public BaseResponse<Long> seatAdd(@RequestBody Seat seat, HttpServletRequest httpServletRequest) {
        if (seat == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(httpServletRequest);
        long seatId = seatService.seatAdd(seat,loginUser);

        return ResultUtils.success(seatId);

    }
    @PostMapping("/update")
    public BaseResponse<Integer> seatUpdate(@RequestBody Seat seat, HttpServletRequest httpServletRequest) {
        if (seat == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(httpServletRequest);

        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }

        int result = seatService.updateSeat(seat, loginUser);

        return ResultUtils.success(result);

    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> seatDelete(@RequestBody long id, HttpServletRequest httpServletRequest) {

        if (!userService.isAdmin(httpServletRequest)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "没有权限");
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "查询的id不存在");
        }
        //已经开启了逻辑删除
        boolean result = seatService.removeById(id);

        return ResultUtils.success(result);

    }
}
