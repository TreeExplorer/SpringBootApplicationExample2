create table sys_notice
(
    id            varchar(255)                       not null comment '主键'
        primary key,
    notice_text   varchar(4096)                      null comment '公告内容',
    notice_status tinyint  default 1                 null comment '公告状态',
    show_status   tinyint  default 1                 null comment '是否显示',
    notice_images varchar(1024)                      null comment '图片地址',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    del_status    tinyint  default 0                 null comment '删除标识'
)
    comment '系统公告表' charset = utf8mb4;

create table sys_user
(
    id             varchar(255)                       not null comment '主键'
        primary key,
    account        varchar(255)                       not null comment '账号',
    password       varchar(255)                       not null comment '密码',
    user_name      varchar(255)                       not null comment '用户名',
    sex            tinyint                            null comment '性别',
    birthday       date                               null comment '生日',
    avatar_address varchar(255)                       null comment '头像地址',
    introduce      varchar(255)                       null comment '简介',
    user_role      int                                not null comment '用户角色',
    create_time    datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    del_status     tinyint  default 0                 null comment '删除标志'
)
    comment '系统用户表' charset = utf8mb4;
