package com.wbz.multithreading.cases;

import java.util.concurrent.Semaphore;

/**
 * 信号量示例
 * 演示如何使用Semaphore控制同时访问共享资源的线程数量
 * 信号量可以用于实现资源池、限流等场景
 */
public final class SemaphoreCase {

    private SemaphoreCase() {
    }

    /**
     * 运行信号量示例
     * 演示4个线程竞争2个许可，控制并发访问数量
     */
    public static void run() {
        Semaphore semaphore = new Semaphore(2);
        Thread[] workers = new Thread[4];

        for (int i = 0; i < workers.length; i++) {
            int workerId = i + 1;
            workers[i] = new Thread(() -> useResource(semaphore, workerId), "semaphore-worker-" + workerId);
            workers[i].start();
        }

        for (Thread worker : workers) {
            join(worker);
        }
    }

    /**
     * 使用共享资源的辅助方法
     * @param semaphore 信号量
     * @param workerId 工作者ID
     */
    private static void useResource(Semaphore semaphore, int workerId) {
        try {
            semaphore.acquire();
            System.out.printf("[%s] 拿到许可，开始访问共享资源 %d%n", Thread.currentThread().getName(), workerId);
            Thread.sleep(80);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Semaphore 示例被中断", e);
        } finally {
            semaphore.release();
            System.out.printf("[%s] 释放许可%n", Thread.currentThread().getName());
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
            throw new IllegalStateException("等待 Semaphore 示例线程结束时被中断", e);
        }
    }
}
