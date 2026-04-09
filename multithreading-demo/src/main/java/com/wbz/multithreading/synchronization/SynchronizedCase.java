package com.wbz.multithreading.synchronization;

/**
 * synchronized示例（委托类）
 * 委托给cases包中的实现
 */
public final class SynchronizedCase {

    private SynchronizedCase() {
    }

    /**
     * 运行synchronized示例
     */
    public static void run() {
        com.wbz.multithreading.cases.SynchronizedCase.run();
    }
}
