package com.wbz.multithreading;

import com.wbz.multithreading.async.CompletableFutureCase;
import com.wbz.multithreading.async.ExecutorServiceCase;
import com.wbz.multithreading.async.ForkJoinCase;
import com.wbz.multithreading.coordination.BlockingQueueCase;
import com.wbz.multithreading.coordination.CooperationCase;
import com.wbz.multithreading.coordination.CyclicBarrierCase;
import com.wbz.multithreading.coordination.PhaserCase;
import com.wbz.multithreading.coordination.SemaphoreCase;
import com.wbz.multithreading.foundation.BasicThreadCase;
import com.wbz.multithreading.foundation.FutureAndCallableCase;
import com.wbz.multithreading.foundation.ThreadLocalCase;
import com.wbz.multithreading.loom.VirtualThreadCase;
import com.wbz.multithreading.synchronization.DeadlockCase;
import com.wbz.multithreading.synchronization.LockCase;
import com.wbz.multithreading.synchronization.StampedLockCase;
import com.wbz.multithreading.synchronization.SynchronizedCase;

/**
 * 学习主题：多线程演示工程主入口
 * 核心知识：按照知识点分类顺序串联所有示例，方便集中运行与回顾。
 * 运行说明：直接运行 main 方法，即可依次观察各个并发主题的演示效果。
 */
public class Main {

    public static void main(String[] args) {
        run("1. Thread Foundations", BasicThreadCase::run);
        run("2. Callable and Future", FutureAndCallableCase::run);
        run("3. synchronized", SynchronizedCase::run);
        run("4. Lock", LockCase::run);
        run("5. Deadlock Awareness", DeadlockCase::run);
        run("6. wait/notify and CountDownLatch", CooperationCase::run);
        run("7. BlockingQueue", BlockingQueueCase::run);
        run("8. ExecutorService", ExecutorServiceCase::run);
        run("9. CompletableFuture", CompletableFutureCase::run);
        run("10. ThreadLocal", ThreadLocalCase::run);
        run("11. Semaphore", SemaphoreCase::run);
        run("12. CyclicBarrier", CyclicBarrierCase::run);
        run("13. Phaser", PhaserCase::run);
        run("14. StampedLock", StampedLockCase::run);
        run("15. ForkJoinPool", ForkJoinCase::run);
        run("16. Virtual Threads", VirtualThreadCase::run);
    }

    private static void run(String title, Runnable action) {
        System.out.println();
        System.out.println("=".repeat(18) + " " + title + " " + "=".repeat(18));
        action.run();
    }
}