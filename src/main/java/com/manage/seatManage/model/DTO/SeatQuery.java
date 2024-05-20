package com.manage.seatManage.model.DTO;

import com.baomidou.mybatisplus.annotation.*;
import com.manage.seatManage.model.request.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 座位表
 * @TableName seat
 */
@Data
public class SeatQuery extends PageRequest implements Serializable {
    /**
     * id
     */

    private List<Long> idList;

    /**
     * 座位号
     */
    private Integer seatNumber;

    /**
     * 楼层
     */
    private Integer floor;
    /**
     * 区域
     */
    private Integer type_;

    /**
     * 过期时间
     */
    private Date choiceTime;

    /**
     * 0 - 可预约，1 - 已预约，2 - 已占用
     */
    private Integer status;
}