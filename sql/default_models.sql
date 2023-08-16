-- 用户表
create table if not exists user (
    user_id         bigint(16)      auto_increment not null             comment '用户标识',
    account         varchar(128)    not null                            comment '账号',
    password        varchar(512)    not null                            comment '密码',
    password_salt   varchar(128)    not null                            comment '密码盐',
    username        varchar(128)    null                                comment '用户昵称',
    avatar          varchar(1024)   null                                comment '用户头像',
    profile         varchar(512)    null                                comment '用户简介',
    system_role     varchar(64)     not null default 'user'             comment '系统角色：user/admin/ban',
    create_time     datetime        not null default current_timestamp  comment '创建时间',
    update_time     datetime        not null default current_timestamp on update current_timestamp comment '修改时间',
    valid           tinyint(1)      not null default 1                  comment '是否有效',
    primary key (user_id),
    unique key idx_user_account (account)
) engine=innodb auto_increment=1 default charset=utf8 comment='user';
