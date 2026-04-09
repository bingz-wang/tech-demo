package com.wbz.multithreading.async;

/**
 * 线程池示例（委托类）
 * 委托给cases包中的实现
 */
public final class ExecutorServiceCase {

    private ExecutorServiceCase() {
    }

    /**
     * 运行线程池示例
     */
    public static void run() {
        com.wbz.multithreading.cases.ExecutorServiceCase.run();
    }
}
