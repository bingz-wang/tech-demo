package com.wbz.multithreading.cases;

/**
 * 基础线程示例
 * 演示如何创建和启动线程，以及等待线程执行完毕
 */
public final class BasicThreadCase {

    private BasicThreadCase() {
    }

    /**
     * 运行基础线程示例
     * 创建子线程并等待其执行完成
     */
    public static void run() {
        Thread worker = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.printf("[%s] 正在执行第 %d 步%n", Thread.currentThread().getName(), i);
                sleep(80);
            }
        }, "basic-worker");

        worker.start();
        System.out.printf("[%s] 等待子线程执行完毕%n", Thread.currentThread().getName());
        join(worker);
        System.out.println("Thread 示例结束");
    }

    /**
     * 等待线程执行完毕的辅助方法
     * 处理InterruptedException并恢复中断状态
     * @param thread 要等待的线程
     */
    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("等待线程结束时被中断", e);
        }
    }

    /**
     * 线程休眠的辅助方法
     * 处理InterruptedException并恢复中断状态
     * @param millis 休眠时间（毫秒）
     */
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("线程休眠时被中断", e);
        }
    }
}
