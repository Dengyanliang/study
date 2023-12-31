package com.deng.study.designpattern.principle.demeter;

/**
 * @Desc: 迪米特法则
 * @Auther: dengyanliang
 * @Date: 2023/12/29 10:28
 */
public class Test {
    public static void main(String[] args) {
        Boss boss = new Boss();
        TeamLeader teamLeader = new TeamLeader();
        boss.commandCheckNumber(teamLeader);
    }
}
