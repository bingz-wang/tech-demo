package com.wbz.multithreading.loom;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 学习主题：虚拟线程入门
 * 核心知识：体验 Builder 方式与虚拟线程执行器两种创建手段，理解其适合大量轻量阻塞任务的特点。
 * 运行说明：运行后可对比多个任务并发执行的耗时，并观察当前线程名称的变化。
 */
public final class VirtualThreadCase {

    private VirtualThreadCase() {
    }

    public static void run() {
        runWithBuilder();
        runWithExecutor();
    }

    private static void runWithBuilder() {
        List<Thread> threads = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            int taskId = i;
            Thread thread = Thread.ofVirtual().name("virtual-builder-", taskId).start(() -> {
                sleep(Duration.ofMillis(40));
                System.out.printf("[%s] builder task %d%n", Thread.currentThread(), taskId);
            });
            threads.add(thread);
        }
        threads.forEach(VirtualThreadCase::join);
    }

    private static void runWithExecutor() {
        Instant start = Instant.now();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<String>> futures = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                int taskId = i;
                futures.add(executor.submit(() -> {
                    String threadName = Thread.currentThread().toString();
                    sleep(Duration.ofMillis(80));
                    return "virtual executor task " + taskId + " on " + threadName;
                }));
            }

            for (Future<String> future : futures) {
                try {
                    System.out.println(future.get());
                } catch (Exception e) {
                    throw new IllegalStateException("Virtual thread task failed", e);
                }
            }
        }
        System.out.println("virtual thread elapsed = " + Duration.between(start, Instant.now()).toMillis() + " ms");
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for virtual thread", e);
        }
    }

    private static void sleep(Duration duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted during virtual thread demo", e);
        }
    }
}