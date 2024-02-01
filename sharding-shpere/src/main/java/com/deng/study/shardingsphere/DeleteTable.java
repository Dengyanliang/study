package com.deng.study.shardingsphere;

public class DeleteTable {
    public static void main(String[] args) {
        String sourceDatabase = "order_new_db";
        String sourceTable = "t_order";
        String targetDatabase = "";
        String targetTable = "";

        for (Integer i = 0; i < 16; i++) {
            targetDatabase = sourceDatabase + "_" + i;
            for (Integer no = 0; no < 32; no++) {
                if (no.toString().length() == 1) {
                    targetTable = sourceTable + "_" + "000" + no.toString();
                } else if (no.toString().length() == 2) {
                    targetTable = sourceTable + "_" + "00" + no.toString();
                } else {
                    targetTable = sourceTable + "_" + "0" + no.toString();
                }
                String deleteStr = "DROP TABLE " + targetDatabase + "." + targetTable + ";";
                System.out.println(deleteStr);

            }
        }
    }


}
