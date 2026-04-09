package com.wbz.multithreading.synchronization;

/**
 * StampedLock示例（委托类）
 * 委托给cases包中的实现
 */
public final class StampedLockCase {

    private StampedLockCase() {
    }

    /**
     * 运行StampedLock示例
     */
    public static void run() {
        com.wbz.multithreading.cases.StampedLockCase.run();
    }
}
