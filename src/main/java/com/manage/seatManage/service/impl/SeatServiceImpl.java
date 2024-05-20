package com.manage.seatManage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manage.seatManage.common.ErrorCode;
import com.manage.seatManage.exception.BusinessException;
import com.manage.seatManage.model.DTO.MySeatQuery;
import com.manage.seatManage.model.DTO.SeatQuery;
import com.manage.seatManage.model.domain.Seat;
import com.manage.seatManage.model.domain.User;
import com.manage.seatManage.model.domain.UserSeat;
import com.manage.seatManage.service.SeatService;
import com.manage.seatManage.mapper.SeatMapper;
import com.manage.seatManage.service.UserSeatService;
import com.manage.seatManage.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lenovo
 * @description 针对表【seat(座位表)】的数据库操作Service实现
 * @createDate 2024-05-16 23:44:34
 */
@Service
public class SeatServiceImpl extends ServiceImpl<SeatMapper, Seat>
        implements SeatService {


    @Resource
    private UserService userService;

    @Resource
    private SeatMapper seatMapper;

    @Resource
    private UserSeatService userSeatService;


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
        //2管理员修改座位信息
        if (!userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        Seat oldSeat = seatMapper.selectById(seatId);
        //3没有该座位
        if (oldSeat == null) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }

        return seatMapper.updateById(seat);


    }

    @Override
    public boolean reservationSeat(Seat seat, User loginUser) {
        if (seat == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //1.先找到该座位
        //2.找到之后判断是否已经占用或者已经被预约
        long seatId = seat.getId();
        if (seatId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Seat choiceSeat = seatMapper.selectById(seatId);
        if (choiceSeat == null) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        if (choiceSeat.getStatus() == 1 || choiceSeat.getStatus() == 2) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "该座位已被占用或已预约");
        }
        long userId = loginUser.getId();
        //预约成功，修改学生座位关系
        UserSeat userSeat = new UserSeat();
        userSeat.setSeatId(seatId);
        userSeat.setUserId(userId);
        userSeat.setJoinTime(new Date());

        //修改status
        UpdateWrapper<Seat> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",seatId);
        updateWrapper.set("status",2);
        seatMapper.update(updateWrapper);

        return userSeatService.save(userSeat);

    }


    @Override
    public boolean cancelSeat(int id, User loginUser) {

        //1.得到我预约的座位(已实现)
        //2.删除用户座位表中的一行数据
        //3.座位表status更新为0
        if (id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (loginUser==null){
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        //2
        QueryWrapper<UserSeat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("seatId",id);
        userSeatService.remove(queryWrapper);
        //3
        //修改status
        UpdateWrapper<Seat> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("status",0);
        int update = seatMapper.update(updateWrapper);
        if (update!=id){
            return false;
        }
        return true;


    }

    @Override
    public List<Seat> getSeatInfo(SeatQuery seatQuery, User loginUser) {
        if (seatQuery==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<Seat> queryWrapper = new QueryWrapper<>();

        if (seatQuery!=null){

            List<Long> idList = seatQuery.getIdList();
            if (CollectionUtils.isNotEmpty(idList)){
                queryWrapper.eq("id",idList);
            }
            int seatNum = seatQuery.getSeatNumber();
            if (seatNum>=0){
                queryWrapper.eq("seatNum",seatNum);
            }

            int floor = seatQuery.getFloor();
            if (floor>=0){
                queryWrapper.eq("floor",floor);
            }
            int status = seatQuery.getStatus();
            if (status==1||status==2||status==0){
                queryWrapper.eq("status",status);
            }

        }
        List<Seat> seatList = this.list(queryWrapper);
        if (seatList==null){
            return new ArrayList<>();
        }

        return seatList;

    }



    @Override
    public List<Seat> listSeat(MySeatQuery mySeatQuery, User loginUser) {
        if (mySeatQuery==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (loginUser==null){
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        //通过userid得到seatId
        //通过seatId得到seat信息
        QueryWrapper<UserSeat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",loginUser.getId());
        List<UserSeat> userSeatList = userSeatService.list(queryWrapper);

        QueryWrapper<Seat> seatQueryWrapper = new QueryWrapper<>();
        List<Seat> seatList = new ArrayList<>();
        for (UserSeat userSeat : userSeatList) {
            Long seatId  = userSeat.getSeatId();
            Seat seat = seatMapper.selectById(seatId);
            seatList.add(seat);
        }
        return seatList;
    }

}




