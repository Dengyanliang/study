package com.deng.study.shardingsphere;

public class DeleteTable {
    public static void main(String[] args) {
        String sourceDatabase = "order_new_db";
        String sourceTable = "t_order";
        String targetDatabase = "";
        String targetTable = "";

        for (Integer i = 0; i < 4; i++) {
            if(i.toString().length() == 1){
                targetDatabase = sourceDatabase + "_0" + i;
            }else{
                targetDatabase = sourceDatabase + "_" + i;
            }

            for (Integer no = 0; no < 8; no++) {
                if (no.toString().length() == 1) {
                    targetTable = sourceTable + "_" + "000" + no.toString();
                } else if (no.toString().length() == 2) {
                    targetTable = sourceTable + "_" + "00" + no.toString();
                } else {
                    targetTable = sourceTable + "_" + "0" + no.toString();
                }
                String deleteStr = "delete from " + targetDatabase + "." + targetTable + ";";
                System.out.println(deleteStr);

            }
        }
    }


}
