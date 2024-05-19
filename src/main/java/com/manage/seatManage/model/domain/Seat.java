package com.manage.seatManage.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 座位表
 * @TableName seat
 */
@TableName(value ="seat")
@Data
public class Seat implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 座位号
     */
    private String seatNumber;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 用户id（队长 id）
     */
    private Long userId;

    /**
     * 0 - 可预约，1 - 已预约，2 - 已占用
     */
    private Integer status;
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