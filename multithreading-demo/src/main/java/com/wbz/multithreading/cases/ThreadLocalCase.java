package com.wbz.multithreading.cases;

/**
 * ThreadLocal示例
 * 演示如何使用ThreadLocal在线程间隔离数据
 * ThreadLocal为每个使用它的线程提供独立的变量副本
 */
public final class ThreadLocalCase {

    /** 线程本地变量，用于存储追踪ID */
    private static final ThreadLocal<String> TRACE_ID = ThreadLocal.withInitial(() -> "unset");

    private ThreadLocalCase() {
    }

    /**
     * 运行ThreadLocal示例
     * 演示不同线程使用ThreadLocal存储各自的数据
     */
    public static void run() {
        Thread first = new Thread(() -> handleRequest("trace-A"), "request-1");
        Thread second = new Thread(() -> handleRequest("trace-B"), "request-2");

        first.start();
        second.start();

        join(first);
        join(second);
    }

    /**
     * 处理请求，使用ThreadLocal存储traceId
     * @param traceId 追踪ID
     */
    private static void handleRequest(String traceId) {
        try {
            TRACE_ID.set(traceId);
            System.out.printf("[%s] 当前 traceId = %s%n", Thread.currentThread().getName(), TRACE_ID.get());
        } finally {
            TRACE_ID.remove();
        }
    }

    /**
     * 等待线程执行完毕的辅助方法
     * @param thread 要等待的线程
     */
    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("等待 ThreadLocal 示例线程结束时被中断", e);
        }
    }
}
