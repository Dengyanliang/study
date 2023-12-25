drop table toilet;

CREATE TABLE `toilet` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `clean` tinyint(4) NOT NULL DEFAULT '1',
  `available` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 comment='厕所表';