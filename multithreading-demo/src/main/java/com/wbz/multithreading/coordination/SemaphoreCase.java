package com.wbz.multithreading.coordination;

import java.util.concurrent.Semaphore;

/**
 * 学习主题：Semaphore 信号量限流
 * 核心知识：控制同一时刻可访问共享资源的线程数量。
 * 运行说明：运行后可观察只有部分 worker 同时拿到 permit，其余线程需要等待释放。
 */
public final class SemaphoreCase {

    private SemaphoreCase() {
    }

    public static void run() {
        Semaphore semaphore = new Semaphore(2);
        Thread[] workers = new Thread[4];
        for (int i = 0; i < workers.length; i++) {
            int workerId = i + 1;
            workers[i] = new Thread(() -> accessResource(semaphore, workerId), "semaphore-" + workerId);
            workers[i].start();
        }

        for (Thread worker : workers) {
            join(worker);
        }
    }

    private static void accessResource(Semaphore semaphore, int workerId) {
        try {
            semaphore.acquire();
            System.out.printf("[%s] worker %d got permit%n", Thread.currentThread().getName(), workerId);
            Thread.sleep(30L * workerId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted during Semaphore demo", e);
        } finally {
            semaphore.release();
        }
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for Semaphore demo", e);
        }
    }
}