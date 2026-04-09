package com.wbz.multithreading.cases;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock接口示例
 * 演示如何使用ReentrantLock实现线程安全的共享资源访问
 * 与synchronized相比，Lock提供更灵活的锁定操作，可中断、可超时、可尝试获取锁
 */
public final class LockCase {

    private LockCase() {
    }

    /**
     * 运行Lock示例
     * 演示多个线程使用Lock安全地递增计数器
     */
    public static void run() {
        SafeCounter counter = new SafeCounter();
        Thread first = new Thread(() -> increment(counter), "lock-worker-1");
        Thread second = new Thread(() -> increment(counter), "lock-worker-2");

        first.start();
        second.start();

        join(first);
        join(second);
        System.out.println("Lock 保护后的计数结果：" + counter.value());
    }

    /**
     * 递增辅助方法
     * @param counter 安全计数器
     */
    private static void increment(SafeCounter counter) {
        for (int i = 0; i < 1_000; i++) {
            counter.increment();
        }
    }

    /**
     * 等待线程执行完毕的辅助方法
     * @param thread 要等待的线程
     */
    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("等待 Lock 示例线程结束时被中断", e);
        }
    }

    /**
     * 安全计数器类，使用ReentrantLock保证线程安全
     */
    private static final class SafeCounter {
        /** ReentrantLock实例 */
        private final Lock lock = new ReentrantLock();
        /** 计数值 */
        private int value;

        /**
         * 递增计数值，使用Lock保证原子性
         * 在finally块中释放锁，确保锁一定会被释放
         */
        public void increment() {
            lock.lock();
            try {
                value++;
            } finally {
                lock.unlock();
            }
        }

        /**
         * 获取当前计数值
         * @return 当前计数值
         */
        public int value() {
            lock.lock();
            try {
                return value;
            } finally {
                lock.unlock();
            }
        }
    }
}
