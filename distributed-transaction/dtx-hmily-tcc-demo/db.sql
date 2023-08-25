

DROP DATABASE IF EXISTS dtx_order;
CREATE DATABASE dtx_order;
create table dtx_order.orders
(
    id               int auto_increment primary key,
    user_id          int                                null,
    product_id       int                                null,
    pay_amount       decimal                            null,
    add_time         datetime default CURRENT_TIMESTAMP null,
    last_update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

-- auto-generated definition
create table dtx_order.tcc_local_try_log
(
    tx_no       varchar(64) not null comment '事务id',
    create_time datetime     not null,
    primary key (tx_no)
) engine = InnoDB default  charset=utf8;

create table dtx_order.tcc_local_confirm_log
(
    tx_no       varchar(64) not null comment '事务id',
    create_time datetime     not null,
    primary key (tx_no)
) engine = InnoDB default  charset=utf8;

create table dtx_order.tcc_local_cancel_log
(
    tx_no       varchar(64) not null comment '事务id',
    create_time datetime     not null,
    primary key (tx_no)
) engine = InnoDB default  charset=utf8;

DROP DATABASE IF EXISTS dtx_account;
CREATE DATABASE dtx_account;
CREATE TABLE dtx_account.account
(
    id               INT(11) NOT NULL AUTO_INCREMENT,
    balance          DOUBLE   DEFAULT NULL,
    last_update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE = InnoDB AUTO_INCREMENT = 1  DEFAULT CHARSET = utf8;

create table dtx_account.tcc_local_try_log
(
    tx_no       varchar(64) not null comment '事务id',
    create_time datetime     not null,
    primary key (tx_no)
) engine = InnoDB default  charset=utf8;

create table dtx_account.tcc_local_confirm_log
(
    tx_no       varchar(64) not null comment '事务id',
    create_time datetime     not null,
    primary key (tx_no)
) engine = InnoDB default  charset=utf8;

create table dtx_account.tcc_local_cancel_log
(
    tx_no       varchar(64) not null comment '事务id',
    create_time datetime     not null,
    primary key (tx_no)
) engine = InnoDB default  charset=utf8;

