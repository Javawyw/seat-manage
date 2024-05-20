package com.manage.seatManage.model.DTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.manage.seatManage.model.request.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 座位表
 * @TableName seat
 */
@TableName(value ="seat")
@Data
public class AllSeatQuery  implements Serializable {
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
     * 0 - 可预约，1 - 已预约，2 - 已占用
     */
    private Integer status;

}