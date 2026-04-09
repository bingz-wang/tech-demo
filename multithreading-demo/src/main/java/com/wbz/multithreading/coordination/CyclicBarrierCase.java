package com.wbz.multithreading.coordination;

/**
 * 循环屏障示例（委托类）
 * 委托给cases包中的实现
 */
public final class CyclicBarrierCase {

    private CyclicBarrierCase() {
    }

    /**
     * 运行循环屏障示例
     */
    public static void run() {
        com.wbz.multithreading.cases.CyclicBarrierCase.run();
    }
}
