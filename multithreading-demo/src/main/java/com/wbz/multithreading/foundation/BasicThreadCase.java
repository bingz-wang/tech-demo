package com.wbz.multithreading.foundation;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 学习主题：线程基础与常用 API
 * 核心知识：演示继承 Thread、实现 Runnable、配合 FutureTask 使用 Callable，以及名称、sleep、yield、join、优先级、守护线程等基础能力。
 * 运行说明：该示例适合最先运行，用来建立对线程创建方式和生命周期观察点的直观认识。
 */
public final class BasicThreadCase {

    private BasicThreadCase() {
    }

    public static void run() {
        demonstrateThreadSubclass();
        demonstrateRunnable();
        demonstrateCallable();
        demonstrateThreadApis();
    }

    private static void demonstrateThreadSubclass() {
        Thread first = new NamedThread("thread-subclass-1");
        Thread second = new NamedThread("thread-subclass-2");
        first.start();
        second.start();
        join(first);
        join(second);
    }

    private static void demonstrateRunnable() {
        Runnable task = () -> {
            for (int i = 1; i <= 3; i++) {
                System.out.printf("[%s] Runnable task step %d%n", Thread.currentThread().getName(), i);
                BasicThreadCase.sleep(20);
            }
        };

        Thread first = new Thread(task, "runnable-1");
        Thread second = new Thread(task, "runnable-2");
        first.start();
        second.start();
        join(first);
        join(second);
    }

    private static void demonstrateCallable() {
        Callable<Integer> callable = () -> {
            int sum = 0;
            for (int i = 1; i <= 100; i++) {
                sum += i;
            }
            return sum;
        };

        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        Thread worker = new Thread(futureTask, "callable-worker");
        worker.start();
        join(worker);
        try {
            System.out.println("Callable result = " + futureTask.get());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to get Callable result", e);
        }
    }

    private static void demonstrateThreadApis() {
        Thread apiThread = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.printf("[%s] currentThread=%s, priority=%d%n",
                        Thread.currentThread().getName(),
                        Thread.currentThread().getName(),
                        Thread.currentThread().getPriority());
                if (i == 2) {
                    Thread.yield();
                }
                sleep(15);
            }
        }, "api-worker");
        apiThread.setPriority(Thread.MAX_PRIORITY);

        Thread daemonThread = new Thread(() -> {
            int round = 1;
            while (!Thread.currentThread().isInterrupted() && round <= 10) {
                System.out.printf("[%s] daemon heartbeat %d%n", Thread.currentThread().getName(), round++);
                sleep(10);
            }
        }, "daemon-helper");
        daemonThread.setDaemon(true);

        daemonThread.start();
        apiThread.start();
        join(apiThread);
        System.out.printf("[%s] join finished, main flow continues%n", Thread.currentThread().getName());
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for thread", e);
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted during sleep", e);
        }
    }

    private static final class NamedThread extends Thread {

        private NamedThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 1; i <= 3; i++) {
                System.out.printf("[%s] Thread subclass step %d%n", getName(), i);
                BasicThreadCase.sleep(20);
            }
        }
    }
}