package com.wbz.multithreading.foundation;

/**
 * 学习主题：ThreadLocal 线程隔离
 * 核心知识：为每个线程维护独立副本，避免共享变量在并发场景下相互污染。
 * 运行说明：运行后可观察两个线程分别读取到自己的 traceId，而不会互相覆盖。
 */
public final class ThreadLocalCase {

    private static final ThreadLocal<String> TRACE_ID = ThreadLocal.withInitial(() -> "unset");

    private ThreadLocalCase() {
    }

    public static void run() {
        Thread first = new Thread(() -> handleRequest("trace-A"), "request-1");
        Thread second = new Thread(() -> handleRequest("trace-B"), "request-2");

        first.start();
        second.start();

        join(first);
        join(second);
    }

    private static void handleRequest(String traceId) {
        try {
            TRACE_ID.set(traceId);
            System.out.printf("[%s] traceId = %s%n", Thread.currentThread().getName(), TRACE_ID.get());
        } finally {
            TRACE_ID.remove();
        }
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for ThreadLocal demo", e);
        }
    }
}