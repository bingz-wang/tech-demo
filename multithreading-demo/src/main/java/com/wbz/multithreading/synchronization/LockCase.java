package com.wbz.multithreading.synchronization;

/**
 * Lock示例（委托类）
 * 委托给cases包中的实现
 */
public final class LockCase {

    private LockCase() {
    }

    /**
     * 运行Lock示例
     */
    public static void run() {
        com.wbz.multithreading.cases.LockCase.run();
    }
}
