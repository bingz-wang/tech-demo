package com.wbz.multithreading.synchronization;

/**
 * 学习主题：死锁认知与规避
 * 核心知识：通过统一加锁顺序说明死锁产生的条件，以及项目中常见的规避思路。
 * 运行说明：本示例不会真的制造卡死，而是用安全写法帮助理解“固定锁顺序”这一实践原则。
 */
public final class DeadlockCase {

    private DeadlockCase() {
    }

    public static void run() {
        Object lockA = new Object();
        Object lockB = new Object();

        Thread first = new Thread(() -> acquireInOrder("worker-A", lockA, lockB), "deadlock-worker-A");
        Thread second = new Thread(() -> acquireInOrder("worker-B", lockA, lockB), "deadlock-worker-B");

        first.start();
        second.start();

        join(first);
        join(second);
        System.out.println("deadlock case finished: unified lock order prevents deadlock");
    }

    private static void acquireInOrder(String label, Object firstLock, Object secondLock) {
        synchronized (firstLock) {
            System.out.println(label + " acquired lockA");
            sleep(20);
            synchronized (secondLock) {
                System.out.println(label + " acquired lockB");
            }
        }
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for deadlock demo", e);
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted during deadlock demo", e);
        }
    }
}