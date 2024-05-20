package com.manage.seatManage.service;

import com.manage.seatManage.model.DTO.MySeatQuery;
import com.manage.seatManage.model.DTO.SeatQuery;
import com.manage.seatManage.model.domain.Seat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.manage.seatManage.model.domain.User;

import java.util.List;

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

    /**
     * 座位预约
     */
    boolean reservationSeat(Seat seat,User loginUser);

    /**
     * 座位取消
     * @return
     */
    boolean cancelSeat(int id, User loginUser);

    /**
     * 得到座位信息
     */
    List<Seat> getSeatInfo(SeatQuery seatQuery,User loginUser);
    /**
     * 得到我预约的座位
     */
    List<Seat> listSeat(MySeatQuery mySeatQuery,User loginUser);
}
