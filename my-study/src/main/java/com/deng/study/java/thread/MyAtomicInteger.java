package com.deng.study.java.thread;

import sun.misc.Unsafe;

/**
 * @Desc: 自定义AtomicInteger
 * @Auther: dengyanliang
 * @Date: 2023/2/16 11:19
 */
public class MyAtomicInteger implements Account{

    private volatile int value; // 传入的值
    private static final long valueOffset; //字段域的偏移地址
    private static final Unsafe UNSAFE;

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    static {
        UNSAFE = UnsafeAccessor.getUnsafe();
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int getValue() {
        return value;
    }

    /**
     * 使用自定义的cas实现取款操作
     * @param amount
     */
    public void decrement(int amount) {
        while (true) {
            int prev = this.value;
            int next = prev - amount;
            if (UNSAFE.compareAndSwapInt(this, valueOffset, prev, next)) {
                break;
            }
        }
    }

    @Override
    public Integer getBalance() {
        return getValue();
    }

    @Override
    public void withdraw(Integer amount) {
        decrement(amount);
    }
}
