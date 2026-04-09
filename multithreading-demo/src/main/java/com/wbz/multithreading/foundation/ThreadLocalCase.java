package com.wbz.multithreading.foundation;

/**
 * ThreadLocal示例（委托类）
 * 委托给cases包中的实现
 */
public final class ThreadLocalCase {

    private ThreadLocalCase() {
    }

    /**
     * 运行ThreadLocal示例
     */
    public static void run() {
        com.wbz.multithreading.cases.ThreadLocalCase.run();
    }
}
