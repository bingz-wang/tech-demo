package com.wbz.multithreading.coordination;

/**
 * 线程协作示例（委托类）
 * 委托给cases包中的实现
 */
public final class CooperationCase {

    private CooperationCase() {
    }

    /**
     * 运行线程协作示例
     */
    public static void run() {
        com.wbz.multithreading.cases.CooperationCase.run();
    }
}
