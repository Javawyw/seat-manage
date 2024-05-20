create table user
(
    id           bigint auto_increment
        primary key,
    username     varchar(255)                       null comment '姓名',
    gender       tinyint                            null comment '性别',
    userAccount  varchar(255)                       null comment '学号',
    userPassword varchar(255)                       not null comment '密码',
    phone        varchar(255)                       null comment '电话',
    isValid      tinyint  default 0                 not null comment '是否有效',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 null comment '是否删除',
    userStatus   int      default 0                 not null,
    userRole     tinyint  default 0                 not null comment '角色 0 普通用户 1管理员 '
)
    comment '用户表';


create table seat
(
    id         bigint auto_increment comment 'id' primary key,
    seatNumber int                null comment '座位号',
    floor      int                                null comment '楼层',
    type       int                                null comment '区域'
    expireTime datetime                           null comment '过期时间',
    userId     bigint comment '用户id（队长 id）',
    status     int      default 0                 not null comment '0 - 可预约，1 - 已预约，2 - 已占用',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete   tinyint  default 0                 not null comment '是否删除'
) comment '座位表';

-- 用户座位关系
create table user_seat
(
    id             bigint auto_increment comment 'id'
        primary key,
    userId         bigint comment '用户id',
    seatId         bigint comment '座位id',
    joinTime       datetime                           null comment '预约时间',
    joinInSeatTime datetime                           null comment '入座时间',
    createTime     datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime     datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete       tinyint  default 0                 not null comment '是否删除'
) comment '用户座位关系';


