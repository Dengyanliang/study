package com.deng.study.java.thread;

import java.util.Random;

/**
 * @Desc: 测试转账的安全性
 * @Auther: dengyanliang
 * @Date: 2023/2/17 19:59
 */
public class TransferAmountTest {

    static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        TransferAmount a = new TransferAmount(1000);
        TransferAmount b = new TransferAmount(1000);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                a.transfer(b,randomAmount());
            }
        },"t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                b.transfer(a,randomAmount());
            }
        },"t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("总金额:" + (a.getMoney()+b.getMoney()));

    }

    private static int randomAmount() {
        return random.nextInt(10) + 1;
    }

}

class TransferAmount{
    private int money;

    public TransferAmount(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void transfer(TransferAmount target, int amount) {
        synchronized (TransferAmount.class) {
            if (this.money >= amount) {
                this.setMoney(this.getMoney() - amount);
                target.setMoney(target.getMoney() + amount);
            }
        }
    }
}

