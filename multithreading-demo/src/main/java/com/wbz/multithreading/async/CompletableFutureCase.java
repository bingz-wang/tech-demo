package com.wbz.multithreading.async;

/**
 * CompletableFuture示例（委托类）
 * 委托给cases包中的实现
 */
public final class CompletableFutureCase {

    private CompletableFutureCase() {
    }

    /**
     * 运行CompletableFuture示例
     */
    public static void run() {
        com.wbz.multithreading.cases.CompletableFutureCase.run();
    }
}
