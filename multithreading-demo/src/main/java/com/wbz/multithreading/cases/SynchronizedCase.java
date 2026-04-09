package com.wbz.multithreading.cases;

/**
 * synchronized关键字示例
 * 演示如何使用synchronized实现线程安全的共享资源访问
 * synchronized是Java内置的同步机制，提供互斥访问
 */
public final class SynchronizedCase {

    private SynchronizedCase() {
    }

    /**
     * 运行synchronized示例
     * 演示多个线程安全地售卖共享票池中的票
     */
    public static void run() {
        TicketCounter counter = new TicketCounter();
        Thread first = new Thread(() -> sell(counter), "seller-1");
        Thread second = new Thread(() -> sell(counter), "seller-2");

        first.start();
        second.start();

        join(first);
        join(second);
        System.out.println("剩余票数：" + counter.remaining());
    }

    /**
     * 售票辅助方法
     * @param counter 票务计数器
     */
    private static void sell(TicketCounter counter) {
        for (int i = 0; i < 3; i++) {
            counter.sellOne();
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
            throw new IllegalStateException("等待 synchronized 示例线程结束时被中断", e);
        }
    }

    /**
     * 票务计数器类，使用synchronized保证线程安全
     */
    private static final class TicketCounter {
        /** 剩余票数 */
        private int tickets = 5;

        /**
         * 售卖一张票，使用synchronized保证原子性
         * 同一时刻只有一个线程可以执行此方法
         */
        public synchronized void sellOne() {
            if (tickets <= 0) {
                System.out.printf("[%s] 票已经卖完了%n", Thread.currentThread().getName());
                return;
            }

            System.out.printf("[%s] 卖出第 %d 张票%n", Thread.currentThread().getName(), tickets);
            tickets--;
        }

        /**
         * 获取剩余票数
         * @return 剩余票数
         */
        public synchronized int remaining() {
            return tickets;
        }
    }
}
