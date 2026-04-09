package com.wbz.multithreading.cases;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable和Future示例
 * 演示如何使用Callable和FutureTask获取线程执行结果
 * Callable与Runnable类似，但可以返回结果并抛出受检查异常
 */
public final class FutureAndCallableCase {

    private FutureAndCallableCase() {
    }

    /**
     * 运行Callable和Future示例
     * 使用FutureTask包装Callable任务并获取执行结果
     */
    public static void run() {
        Callable<Integer> sumTask = () -> {
            int sum = 0;
            for (int i = 1; i <= 5; i++) {
                sum += i;
            }
            System.out.printf("[%s] 计算结果：%d%n", Thread.currentThread().getName(), sum);
            return sum;
        };

        FutureTask<Integer> futureTask = new FutureTask<>(sumTask);
        Thread thread = new Thread(futureTask, "callable-worker");
        thread.start();

        try {
            Integer result = futureTask.get();
            System.out.println("FutureTask 拿到结果：" + result);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("等待 Callable 结果时被中断", e);
        } catch (ExecutionException e) {
            throw new IllegalStateException("Callable 执行失败", e);
        }
    }
}
