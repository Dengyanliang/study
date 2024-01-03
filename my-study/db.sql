create database deng;

-----用于测试主从复制
create database hu;
create database hu2;
create database hu3;

CREATE TABLE `my_order` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，订单id',
   `name` varchar(64) DEFAULT NULL COMMENT '名称',
   `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
   `status` varchar(64) DEFAULT NULL COMMENT '状态',
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
   PRIMARY KEY (`order_id`),
   KEY `idx_createTime` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='订单表';




CREATE TABLE `pay_order` (
  `id` bigint(20)  NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `product_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `order_fee` bigint(20) DEFAULT NULL COMMENT '订单金额，单位：分 (总金额)',
  `pay_status` tinyint(4) DEFAULT NULL COMMENT '新支付状态，0-初始,1-处理中,2-成功,3-失败',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `pay_finish_time` timestamp NULL DEFAULT NULL COMMENT '支付完成时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_createTime` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付订单表';


CREATE TABLE `product` (
  `id` bigint(20)  NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `price` bigint(20) DEFAULT NULL COMMENT '价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='商品表';

CREATE TABLE `t_udict` (
  `id` bigint(20)  NOT NULL AUTO_INCREMENT,
  `value` varchar(64) DEFAULT NULL COMMENT '值',
  `status` varchar(64) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='字典表';