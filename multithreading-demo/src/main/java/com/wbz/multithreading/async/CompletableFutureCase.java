package com.wbz.multithreading.async;

import java.util.concurrent.CompletableFuture;

/**
 * 学习主题：CompletableFuture 异步编排
 * 核心知识：使用链式 API 组合多个异步步骤，理解 thenApply 和 thenCombine 的典型用法。
 * 运行说明：运行后可观察多个异步任务拼接成最终结果的过程。
 */
public final class CompletableFutureCase {

    private CompletableFutureCase() {
    }

    public static void run() {
        String summary = CompletableFuture.supplyAsync(() -> "Java")
                .thenApply(language -> language + " concurrency")
                .thenCombine(CompletableFuture.supplyAsync(() -> "in progress"),
                        (topic, status) -> topic + " - " + status)
                .join();

        System.out.println("CompletableFuture result = " + summary);
    }
}