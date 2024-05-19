package com.manage.seatManage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manage.seatManage.service.UserSeatService;
import com.manage.seatManage.model.domain.UserSeat;
import com.manage.seatManage.mapper.UserSeatMapper;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【user_seat(用户座位关系)】的数据库操作Service实现
* @createDate 2024-05-16 23:47:59
*/
@Service
public class UserSeatServiceImpl extends ServiceImpl<UserSeatMapper, UserSeat>
    implements UserSeatService {

}




