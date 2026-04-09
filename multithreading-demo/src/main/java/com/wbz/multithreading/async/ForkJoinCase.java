package com.wbz.multithreading.async;

/**
 * ForkJoinPool示例（委托类）
 * 委托给cases包中的实现
 */
public final class ForkJoinCase {

    private ForkJoinCase() {
    }

    /**
     * 运行ForkJoinPool示例
     */
    public static void run() {
        com.wbz.multithreading.cases.ForkJoinCase.run();
    }
}
