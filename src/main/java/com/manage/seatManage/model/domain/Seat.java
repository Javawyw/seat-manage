package com.manage.seatManage.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.models.auth.In;
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