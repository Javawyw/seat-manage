package com.manage.seatManage.service;

import com.manage.seatManage.model.domain.Seat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.manage.seatManage.model.domain.User;

/**
* @author lenovo
* @description 针对表【seat(座位表)】的数据库操作Service
* @createDate 2024-05-16 23:44:34
*/
public interface SeatService extends IService<Seat> {
    /**
     * 管理图书馆座位信息(增删改查)
     */
    long seatAdd(Seat seat, User loginUser);

    /**
     * 修改信息
     *
     */
    int updateSeat(Seat seat, User loginUser);


}
