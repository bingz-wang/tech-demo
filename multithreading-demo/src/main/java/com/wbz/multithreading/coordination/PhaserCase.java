package com.wbz.multithreading.coordination;

/**
 * 阶段器示例（委托类）
 * 委托给cases包中的实现
 */
public final class PhaserCase {

    private PhaserCase() {
    }

    /**
     * 运行阶段器示例
     */
    public static void run() {
        com.wbz.multithreading.cases.PhaserCase.run();
    }
}
