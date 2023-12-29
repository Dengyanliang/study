package com.deng.study.design_pattern.principle.demeter;

/**
 * @Auther: yanliangdeng
 * @Date: 2023/12/29 10:28
 * @Description: 迪米特法则
 */
public class Test {
    public static void main(String[] args) {
        Boss boss = new Boss();
        TeamLeader teamLeader = new TeamLeader();
        boss.commandCheckNumber(teamLeader);
    }
}
