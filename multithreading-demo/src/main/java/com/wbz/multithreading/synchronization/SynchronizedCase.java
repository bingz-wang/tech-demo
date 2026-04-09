package com.wbz.multithreading.synchronization;

/**
 * 学习主题：synchronized 基础用法
 * 核心知识：对比同步代码块和同步方法两种写法，理解内置锁在共享资源保护中的作用。
 * 运行说明：运行后可观察多个售票线程在加锁保护下顺序卖票，避免出现重复售卖。
 */
public final class SynchronizedCase {

    private SynchronizedCase() {
    }

    public static void run() {
        demonstrateSynchronizedBlock();
        demonstrateSynchronizedMethod();
    }

    private static void demonstrateSynchronizedBlock() {
        TicketSeller seller = new TicketSeller(6);
        Thread first = new Thread(seller::sellWithBlock, "block-seller-1");
        Thread second = new Thread(seller::sellWithBlock, "block-seller-2");
        Thread third = new Thread(seller::sellWithBlock, "block-seller-3");

        first.start();
        second.start();
        third.start();

        join(first);
        join(second);
        join(third);
        System.out.println("Remaining tickets after synchronized block = " + seller.remaining());
    }

    private static void demonstrateSynchronizedMethod() {
        TicketTask task = new TicketTask(6);
        Thread first = new Thread(task, "method-seller-1");
        Thread second = new Thread(task, "method-seller-2");
        Thread third = new Thread(task, "method-seller-3");

        first.start();
        second.start();
        third.start();

        join(first);
        join(second);
        join(third);
        System.out.println("Remaining tickets after synchronized method = " + task.remaining());
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for synchronized demo", e);
        }
    }

    private static final class TicketSeller {
        private final Object lock = new Object();
        private int tickets;

        private TicketSeller(int tickets) {
            this.tickets = tickets;
        }

        private void sellWithBlock() {
            while (true) {
                synchronized (lock) {
                    if (tickets <= 0) {
                        return;
                    }
                    System.out.printf("[%s] sold ticket #%d%n", Thread.currentThread().getName(), tickets--);
                }
            }
        }

        private int remaining() {
            synchronized (lock) {
                return tickets;
            }
        }
    }

    private static final class TicketTask implements Runnable {
        private int tickets;

        private TicketTask(int tickets) {
            this.tickets = tickets;
        }

        @Override
        public void run() {
            while (!sellOne()) {
                // loop until sold out
            }
        }

        private synchronized boolean sellOne() {
            if (tickets <= 0) {
                return true;
            }
            System.out.printf("[%s] sold ticket #%d%n", Thread.currentThread().getName(), tickets--);
            return false;
        }

        private synchronized int remaining() {
            return tickets;
        }
    }
}