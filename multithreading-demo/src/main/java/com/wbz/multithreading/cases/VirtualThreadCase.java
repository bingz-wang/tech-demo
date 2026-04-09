package com.wbz.multithreading.cases;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * 虚拟线程示例
 * 演示Java 21+引入的虚拟线程特性
 * 虚拟线程是轻量级线程，适合大量IO密集型任务
 */
public final class VirtualThreadCase {

    private VirtualThreadCase() {
    }

    /**
     * 运行虚拟线程示例
     * 演示两种创建虚拟线程的方式：Builder API和ExecutorService
     */
    public static void run() {
        runWithVirtualThreadBuilder();
        runWithVirtualThreadPerTaskExecutor();
        System.out.println("补充说明：Structured Concurrency 在当前 JDK 25 环境下仍需 --enable-preview，建议后续单独开预览参数练习。");
    }

    /**
     * 使用Thread.ofVirtual() Builder API创建虚拟线程
     */
    private static void runWithVirtualThreadBuilder() {
        List<Thread> threads = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            int taskId = i;
            Thread thread = Thread.ofVirtual().name("virtual-builder-", taskId).start(() -> {
                sleep(Duration.ofMillis(60));
                System.out.printf("[%s] 完成 IO 型任务 %d%n", Thread.currentThread(), taskId);
            });
            threads.add(thread);
        }
        threads.forEach(VirtualThreadCase::join);
    }

    /**
     * 使用Executors.newVirtualThreadPerTaskExecutor()创建虚拟线程执行器
     * 每个任务都在一个新的虚拟线程中执行
     */
    private static void runWithVirtualThreadPerTaskExecutor() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<java.util.concurrent.Future<String>> futures = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                int taskId = i;
                futures.add(executor.submit(() -> {
                    sleep(Duration.ofMillis(40));
                    return "executor-task-" + taskId + " -> " + Thread.currentThread();
                }));
            }

            for (var future : futures) {
                try {
                    System.out.println(future.get());
                } catch (Exception e) {
                    throw new IllegalStateException("虚拟线程执行器任务失败", e);
                }
            }
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
            throw new IllegalStateException("等待虚拟线程结束时被中断", e);
        }
    }

    /**
     * 线程休眠的辅助方法
     * @param duration 休眠时长
     */
    private static void sleep(Duration duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("虚拟线程休眠时被中断", e);
        }
    }
}
