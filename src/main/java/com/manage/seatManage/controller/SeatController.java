package com.manage.seatManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.manage.seatManage.Constant.UserConstant;
import com.manage.seatManage.common.BaseResponse;
import com.manage.seatManage.common.ErrorCode;
import com.manage.seatManage.common.ResultUtils;
import com.manage.seatManage.exception.BusinessException;
import com.manage.seatManage.model.DTO.MySeatQuery;
import com.manage.seatManage.model.DTO.SeatQuery;
import com.manage.seatManage.model.domain.Seat;
import com.manage.seatManage.model.domain.User;
import com.manage.seatManage.model.domain.UserSeat;
import com.manage.seatManage.service.SeatService;
import com.manage.seatManage.service.UserSeatService;
import com.manage.seatManage.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/seat")
@CrossOrigin
@Slf4j
public class SeatController implements UserConstant {
    @Resource
    private SeatService seatService;

    @Resource
    private UserService userService;

    @Resource
    private UserSeatService userSeatService;


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

    @PostMapping("/reservationSeat")
    public BaseResponse<Boolean> seatReservation(@RequestBody Seat seat,HttpServletRequest httpServletRequest){
        if (seat==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(httpServletRequest);
        if (loginUser==null){
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        boolean res = seatService.reservationSeat(seat,loginUser);

        return ResultUtils.success(res);
    }

    @GetMapping("/list")
    public BaseResponse<Page<Seat>> getSeat(SeatQuery seatQuery){
        if (seatQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Seat seat = new Seat();
        BeanUtils.copyProperties(seatQuery, seat);

        Page<Seat> page = new Page<>(seatQuery.getPageNum(), seatQuery.getPageSize());
        QueryWrapper<Seat> queryWrapper = new QueryWrapper<>(seat);
        Page<Seat> resultPage = seatService.page(page, queryWrapper);

        return ResultUtils.success(resultPage);

    }
    @GetMapping("/list/my/seat")
    public BaseResponse<List<Seat>> getMySeat(MySeatQuery mySeatQuery, HttpServletRequest httpServletRequest){
        if (mySeatQuery==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(httpServletRequest);
        List<Seat> seatList = seatService.listSeat(mySeatQuery,loginUser);

        return ResultUtils.success(seatList);

    }
    @PostMapping("/cancel")
    public BaseResponse<Boolean> cancelSeat(@RequestBody  int id,HttpServletRequest httpServletRequest){
        if (id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);

        }
        User loginUser = userService.getLoginUser(httpServletRequest);
        boolean res = seatService.cancelSeat(id,loginUser);

        return ResultUtils.success(res);
    }
}
