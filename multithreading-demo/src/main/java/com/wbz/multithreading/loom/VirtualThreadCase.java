package com.wbz.multithreading.loom;

/**
 * 虚拟线程示例（委托类）
 * 委托给cases包中的实现
 */
public final class VirtualThreadCase {

    private VirtualThreadCase() {
    }

    /**
     * 运行虚拟线程示例
     */
    public static void run() {
        com.wbz.multithreading.cases.VirtualThreadCase.run();
    }
}
