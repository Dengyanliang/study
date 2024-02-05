package com.deng.study.shardingsphere;

public class CreateTable {

    public static void main(String[] args) {
        String sourceDatabase = "order_new_db";
        String sourceTable = "t_order";
        String targetDatabase;
        String targetTable;

        for (Integer i = 0; i < 2; i++) {
            targetDatabase = sourceDatabase + "_" + i;
            for (Integer no = 0; no < 32; no++) {
                if (no.toString().length() == 1) {
                    targetTable = sourceTable + "_" + "000" + no.toString();
                } else if (no.toString().length() == 2) {
                    targetTable = sourceTable + "_" + "00" + no.toString();
                } else if (no.toString().length() == 3){
                    targetTable = sourceTable + "_" + "0" + no.toString();
                } else {
                    targetTable = sourceTable + "_" + no.toString();
                }
                String targetStr = "CREATE TABLE " + targetDatabase + "." + targetTable
//                        + " LIKE " + sourceDatabase + "." + sourceTable
                +"("
                        +  "id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，订单id',"
                        +  "order_no varchar(64) DEFAULT NULL COMMENT '订单号',"
                        +  "name varchar(64) DEFAULT NULL COMMENT '名称',"
                        +  "user_id bigint(20) DEFAULT NULL COMMENT '用户id',"
                        +  "status varchar(64) DEFAULT NULL COMMENT '状态',"
                        +  "create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',"
                        +  "update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',"
                        +  "PRIMARY KEY (id),"
                        +  "KEY idx_createTime (create_time) USING BTREE"
                        +") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='"+targetTable+"';";

                System.out.println(targetStr);
            }
        }


    }
}
