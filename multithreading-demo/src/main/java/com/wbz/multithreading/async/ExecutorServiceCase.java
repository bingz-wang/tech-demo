package com.wbz.multithreading.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 学习主题：线程池基础与配置
 * 核心知识：对比固定线程池、缓存线程池、单线程池和自定义 ThreadPoolExecutor 的典型差异。
 * 运行说明：运行后可观察不同线程池的任务分配效果，并结合参数理解线程池调度行为。
 */
public final class ExecutorServiceCase {

    private ExecutorServiceCase() {
    }

    public static void run() {
        runFixedThreadPool();
        runCachedThreadPool();
        runSingleThreadExecutor();
        runCustomThreadPoolExecutor();
    }

    private static void runFixedThreadPool() {
        try (ExecutorService executor = Executors.newFixedThreadPool(3)) {
            submitTasks(executor, "fixed", 4);
        }
    }

    private static void runCachedThreadPool() {
        try (ExecutorService executor = Executors.newCachedThreadPool()) {
            submitTasks(executor, "cached", 4);
        }
    }

    private static void runSingleThreadExecutor() {
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            submitTasks(executor, "single", 3);
        }
    }

    private static void runCustomThreadPoolExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,
                3,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        try {
            submitTasks(executor, "custom", 6);
            System.out.printf("custom pool stats: core=%d, max=%d, queueSize=%d%n",
                    executor.getCorePoolSize(),
                    executor.getMaximumPoolSize(),
                    executor.getQueue().size());
        } finally {
            executor.shutdown();
            awaitTermination(executor);
        }
    }

    private static void submitTasks(ExecutorService executor, String label, int count) {
        List<Runnable> tasks = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            int taskId = i;
            tasks.add(() -> {
                System.out.printf("[%s] %s-task-%d%n", Thread.currentThread().getName(), label, taskId);
                sleep(20);
            });
        }
        tasks.forEach(executor::submit);
    }

    private static void awaitTermination(ThreadPoolExecutor executor) {
        try {
            if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Custom thread pool did not terminate in time");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for thread pool shutdown", e);
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted during thread pool demo", e);
        }
    }
}