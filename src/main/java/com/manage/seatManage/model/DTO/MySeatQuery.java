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
@TableName(value ="seat")
@Data
public class MySeatQuery extends PageRequest implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 所有预约的座位id
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

}