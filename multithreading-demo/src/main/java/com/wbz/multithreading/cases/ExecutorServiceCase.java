package com.wbz.multithreading.cases;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池示例
 * 演示如何使用ExecutorService管理线程池并提交任务
 * 线程池可以复用线程，减少创建和销毁线程的开销
 */
public final class ExecutorServiceCase {

    private ExecutorServiceCase() {
    }

    /**
     * 运行线程池示例
     * 使用固定大小线程池执行多个任务并获取结果
     */
    public static void run() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(3)) {
            List<Future<String>> futures = new ArrayList<>();

            for (int i = 1; i <= 3; i++) {
                int taskId = i;
                futures.add(executorService.submit(() -> {
                    String result = "pool-task-" + taskId + " 由 " + Thread.currentThread().getName() + " 执行";
                    System.out.println(result);
                    return result;
                }));
            }

            futures.forEach(ExecutorServiceCase::printResult);
        }
    }

    /**
     * 打印Future任务结果的辅助方法
     * @param future Future对象
     */
    private static void printResult(Future<String> future) {
        try {
            System.out.println("拿到线程池结果：" + future.get());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("获取线程池任务结果时被中断", e);
        } catch (ExecutionException e) {
            throw new IllegalStateException("线程池任务执行失败", e);
        }
    }
}
