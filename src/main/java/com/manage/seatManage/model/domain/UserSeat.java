package com.manage.seatManage.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户座位关系
 * @TableName user_seat
 */
@TableName(value ="user_seat")
@Data
public class UserSeat implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 座位id
     */
    private Long seatId;

    /**
     * 预约时间
     */
    private Date joinTime;

    /**
     * 入座时间
     */
    private Date joinInSeatTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}