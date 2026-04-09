package com.wbz.multithreading.cases;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture示例
 * 演示如何使用CompletableFuture进行异步编程和任务组合
 * CompletableFuture提供了强大的函数式编程API来处理异步任务
 */
public final class CompletableFutureCase {

    private CompletableFutureCase() {
    }

    /**
     * 运行CompletableFuture示例
     * 演示异步任务的链式调用和组合
     */
    public static void run() {
        String summary = CompletableFuture.supplyAsync(() -> "Java")
                .thenApply(language -> language + " 并发")
                .thenCombine(
                        CompletableFuture.supplyAsync(() -> "学习中"),
                        (topic, status) -> topic + " - " + status
                )
                .join();

        System.out.println("CompletableFuture 结果：" + summary);
    }
}
