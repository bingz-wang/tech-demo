package com.wbz.multithreading.synchronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 学习主题：ReentrantLock 显式锁
 * 核心知识：理解 lock 和 unlock 的显式配对方式，以及 finally 中释放锁的重要性。
 * 运行说明：运行后可与 synchronized 示例对照，体会显式锁在控制粒度上的灵活性。
 */
public final class LockCase {

    private LockCase() {
    }

    public static void run() {
        LockTicketSeller seller = new LockTicketSeller(6);
        Thread first = new Thread(seller::sell, "lock-seller-1");
        Thread second = new Thread(seller::sell, "lock-seller-2");
        Thread third = new Thread(seller::sell, "lock-seller-3");

        first.start();
        second.start();
        third.start();

        join(first);
        join(second);
        join(third);
        System.out.println("Remaining tickets after ReentrantLock = " + seller.remaining());
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for lock demo", e);
        }
    }

    private static final class LockTicketSeller {
        private final Lock lock = new ReentrantLock();
        private int tickets;

        private LockTicketSeller(int tickets) {
            this.tickets = tickets;
        }

        private void sell() {
            while (true) {
                lock.lock();
                try {
                    if (tickets <= 0) {
                        return;
                    }
                    System.out.printf("[%s] sold ticket #%d%n", Thread.currentThread().getName(), tickets--);
                } finally {
                    lock.unlock();
                }
            }
        }

        private int remaining() {
            lock.lock();
            try {
                return tickets;
            } finally {
                lock.unlock();
            }
        }
    }
}