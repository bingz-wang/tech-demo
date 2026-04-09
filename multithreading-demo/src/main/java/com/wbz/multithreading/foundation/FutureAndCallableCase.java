package com.wbz.multithreading.foundation;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 学习主题：Callable 与 FutureTask
 * 核心知识：演示带返回值的异步任务定义方式，以及如何在主线程中等待并获取执行结果。
 * 运行说明：运行后可重点观察 Callable 与 Runnable 的差异，以及 FutureTask.get 的阻塞行为。
 */
public final class FutureAndCallableCase {

    private FutureAndCallableCase() {
    }

    public static void run() {
        Callable<Integer> sumTask = () -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            System.out.printf("[%s] computed sum=%d%n", Thread.currentThread().getName(), sum);
            return sum;
        };

        FutureTask<Integer> futureTask = new FutureTask<>(sumTask);
        Thread worker = new Thread(futureTask, "future-worker");
        worker.start();

        try {
            System.out.println("FutureTask result = " + futureTask.get());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for FutureTask", e);
        } catch (ExecutionException e) {
            throw new IllegalStateException("Callable execution failed", e);
        }
    }
}