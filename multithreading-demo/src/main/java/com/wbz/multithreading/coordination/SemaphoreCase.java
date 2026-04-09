package com.wbz.multithreading.coordination;

/**
 * 信号量示例（委托类）
 * 委托给cases包中的实现
 */
public final class SemaphoreCase {

    private SemaphoreCase() {
    }

    /**
     * 运行信号量示例
     */
    public static void run() {
        com.wbz.multithreading.cases.SemaphoreCase.run();
    }
}
