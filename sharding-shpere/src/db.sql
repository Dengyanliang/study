
### 用于分库分表
create database order_old_db_1;
create database order_old_db_2;
create database order_old_db_3;
create database order_old_db_4;

CREATE TABLE `order_1` (
  `order_id` bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键，订单id',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `status` varchar(64) DEFAULT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`order_id`),
  KEY `idx_createTime` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表_1';

CREATE TABLE `order_2` (
  `order_id` bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键，订单id',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `status` varchar(64) DEFAULT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`order_id`),
  KEY `idx_createTime` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表_2';

CREATE TABLE `order_3` (
  `order_id` bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键，订单id',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `status` varchar(64) DEFAULT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`order_id`),
  KEY `idx_createTime` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表_3';

CREATE TABLE `order_4` (
  `order_id` bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键，订单id',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `status` varchar(64) DEFAULT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`order_id`),
  KEY `idx_createTime` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表_4';

