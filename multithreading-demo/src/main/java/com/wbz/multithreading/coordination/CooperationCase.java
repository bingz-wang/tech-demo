package com.wbz.multithreading.coordination;

import java.util.concurrent.CountDownLatch;

/**
 * 学习主题：wait/notify 与 CountDownLatch
 * 核心知识：演示传统线程通信方式与倒计时门闩的组合使用，理解线程协作中的等待与唤醒机制。
 * 运行说明：运行后可观察厨师和吃货交替执行，以及主线程如何等待两个任务结束。
 */
public final class CooperationCase {

    private CooperationCase() {
    }

    public static void run() {
        Table table = new Table(4);
        CountDownLatch latch = new CountDownLatch(2);

        Thread cook = new Thread(() -> {
            try {
                table.cook();
            } finally {
                latch.countDown();
            }
        }, "cook");

        Thread foodie = new Thread(() -> {
            try {
                table.eat();
            } finally {
                latch.countDown();
            }
        }, "foodie");

        cook.start();
        foodie.start();
        await(latch);
        System.out.println("wait/notify cooperation finished");
    }

    private static void await(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for cooperation demo", e);
        }
    }

    private static final class Table {
        private final Object lock = new Object();
        private int remainingMeals;
        private boolean hasMealOnDesk;

        private Table(int remainingMeals) {
            this.remainingMeals = remainingMeals;
        }

        private void cook() {
            while (true) {
                synchronized (lock) {
                    while (hasMealOnDesk && remainingMeals > 0) {
                        waitOnLock();
                    }
                    if (remainingMeals == 0) {
                        lock.notifyAll();
                        return;
                    }
                    hasMealOnDesk = true;
                    System.out.printf("[%s] prepared meal, remaining=%d%n",
                            Thread.currentThread().getName(), remainingMeals);
                    lock.notifyAll();
                }
            }
        }

        private void eat() {
            while (true) {
                synchronized (lock) {
                    while (!hasMealOnDesk && remainingMeals > 0) {
                        waitOnLock();
                    }
                    if (remainingMeals == 0 && !hasMealOnDesk) {
                        return;
                    }
                    hasMealOnDesk = false;
                    remainingMeals--;
                    System.out.printf("[%s] ate one meal, left=%d%n",
                            Thread.currentThread().getName(), remainingMeals);
                    lock.notifyAll();
                }
            }
        }

        private void waitOnLock() {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Interrupted during wait/notify demo", e);
            }
        }
    }
}