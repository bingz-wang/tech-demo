package com.wbz.multithreading.foundation;

/**
 * 基础线程示例（委托类）
 * 委托给cases包中的实现
 */
public final class BasicThreadCase {

    private BasicThreadCase() {
    }

    /**
     * 运行基础线程示例
     */
    public static void run() {
        com.wbz.multithreading.cases.BasicThreadCase.run();
    }
}
