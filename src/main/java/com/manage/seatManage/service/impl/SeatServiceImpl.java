package com.manage.seatManage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manage.seatManage.common.ErrorCode;
import com.manage.seatManage.exception.BusinessException;
import com.manage.seatManage.model.domain.Seat;
import com.manage.seatManage.model.domain.User;
import com.manage.seatManage.service.SeatService;
import com.manage.seatManage.mapper.SeatMapper;
import com.manage.seatManage.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【seat(座位表)】的数据库操作Service实现
* @createDate 2024-05-16 23:44:34
*/
@Service
public class SeatServiceImpl extends ServiceImpl<SeatMapper, Seat>
    implements SeatService{


    @Resource
    private UserService userService;

    @Resource
    private SeatMapper seatMapper;


    @Override
    public long seatAdd(Seat seat, User loginUser) {
        //1.请求参数是否为空
        if (seat == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //2.是否登录
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }

        //3.是否为管理员
        if (!userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //保存信息
        boolean saveResult = this.save(seat);
        //返回id值
        Long newSeatId = seat.getId();
        return newSeatId;
    }

    @Override
    public int updateSeat(Seat seat, User loginUser) {
        //1校验
        long seatId = seat.getId();
        if (seatId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //2如果不是管理员，只能自己修改自己
        //3只有管理员才可以修改学生信息
        if (!userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        Seat oldSeat = seatMapper.selectById(seatId);
        //4没有该用户
        if (oldSeat == null) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }

        return seatMapper.updateById(seat);



    }

}




