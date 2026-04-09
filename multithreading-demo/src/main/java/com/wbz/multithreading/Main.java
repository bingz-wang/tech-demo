package com.wbz.multithreading;

import com.wbz.multithreading.async.CompletableFutureCase;
import com.wbz.multithreading.async.ExecutorServiceCase;
import com.wbz.multithreading.async.ForkJoinCase;
import com.wbz.multithreading.coordination.CooperationCase;
import com.wbz.multithreading.coordination.CyclicBarrierCase;
import com.wbz.multithreading.coordination.PhaserCase;
import com.wbz.multithreading.coordination.SemaphoreCase;
import com.wbz.multithreading.foundation.BasicThreadCase;
import com.wbz.multithreading.foundation.FutureAndCallableCase;
import com.wbz.multithreading.foundation.ThreadLocalCase;
import com.wbz.multithreading.loom.VirtualThreadCase;
import com.wbz.multithreading.synchronization.LockCase;
import com.wbz.multithreading.synchronization.StampedLockCase;
import com.wbz.multithreading.synchronization.SynchronizedCase;

/**
 * 多线程编程演示程序主入口
 * 展示了Java并发编程的各个方面，包括：
 * - 基础：Thread创建、Callable/Future、ThreadLocal
 * - 同步：synchronized、Lock、StampedLock
 * - 协作：wait/notify、CountDownLatch、Semaphore、CyclicBarrier、Phaser
 * - 异步：ExecutorService、CompletableFuture、ForkJoinPool
 * - 虚拟线程：Java 21+的虚拟线程特性
 */
public class Main {

    /**
     * 程序入口点，依次运行所有多线程示例
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        run("1. Thread Foundations", BasicThreadCase::run);
        run("2. Callable and Future", FutureAndCallableCase::run);
        run("3. synchronized", SynchronizedCase::run);
        run("4. Lock", LockCase::run);
        run("5. wait/notify and CountDownLatch", CooperationCase::run);
        run("6. ExecutorService", ExecutorServiceCase::run);
        run("7. CompletableFuture", CompletableFutureCase::run);
        run("8. ThreadLocal", ThreadLocalCase::run);
        run("9. Semaphore", SemaphoreCase::run);
        run("10. CyclicBarrier", CyclicBarrierCase::run);
        run("11. Phaser", PhaserCase::run);
        run("12. StampedLock", StampedLockCase::run);
        run("13. ForkJoinPool", ForkJoinCase::run);
        run("14. Virtual Threads", VirtualThreadCase::run);
    }

    /**
     * 运行单个多线程示例的辅助方法
     * @param title 示例标题
     * @param action 要执行的操作
     */
    private static void run(String title, Runnable action) {
        System.out.println();
        System.out.println("=".repeat(18) + " " + title + " " + "=".repeat(18));
        action.run();
    }
}
