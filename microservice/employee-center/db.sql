create table employee_activity
(
    id        int auto_increment comment '主键' primary key,
    `employee_id` int(11) DEFAULT NULL COMMENT '员工ID',
    `resource_id` int(11) DEFAULT NULL COMMENT '如厕ID',
    `activity_type` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '类型',
    `start_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `end_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `active`     tinyint default 1 not null
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='员工活动表';