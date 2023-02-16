package com.deng.study.java.thread;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/15 17:27
 */
public class AccountTest {

    public static void main(String[] args) {
        Account accountByLock = new AccountByLock(10000);
        Account.demo(accountByLock);

        System.out.println("============");

        Account accountCAS = new AccountCAS(10000);
        Account.demo(accountCAS);

        System.out.println("************");
        Account accountAtomicInteger = new MyAtomicInteger(10000);
        Account.demo(accountAtomicInteger);
    }
}

class AccountByLock implements Account{
    private Integer balance;

    public AccountByLock(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return balance;
    }

    /**
     * 如果不对临界区的代码进行加锁处理，则会出现问题
     */
//    @Override
//    public void withdraw(Integer amount) {
//        this.balance -= amount;
//    }

    /**
     * 第一种办法：使用synchronized方法
     * @param amount
     */
    @Override
    public void withdraw(Integer amount) {
        synchronized (this){
            this.balance -= amount;
        }
    }
}

class AccountCAS implements Account{

    private AtomicInteger balance;

    public AccountCAS(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    /**
     * 使用CAS解决
     * @param amount
     */
    @Override
    public void withdraw(Integer amount) {
        while(true){
            int prev = balance.get();
            int next = prev - amount;
            if(balance.compareAndSet(prev,next)){
                break;
            }
        }
    }
}




