package com.wbz.multithreading.coordination;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 学习主题：BlockingQueue 生产者消费者
 * 核心知识：使用阻塞队列简化线程通信逻辑，避免手写 wait/notify。
 * 运行说明：运行后可观察生产者 put 与消费者 take 的自然阻塞过程。
 */
public final class BlockingQueueCase {

    private BlockingQueueCase() {
    }

    public static void run() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        Thread producer = new Thread(() -> produce(queue), "queue-producer");
        Thread consumer = new Thread(() -> consume(queue), "queue-consumer");

        producer.start();
        consumer.start();

        join(producer);
        join(consumer);
    }

    private static void produce(ArrayBlockingQueue<String> queue) {
        try {
            for (int i = 1; i <= 3; i++) {
                String meal = "meal-" + i;
                queue.put(meal);
                System.out.printf("[%s] put %s%n", Thread.currentThread().getName(), meal);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while producing", e);
        }
    }

    private static void consume(ArrayBlockingQueue<String> queue) {
        try {
            for (int i = 1; i <= 3; i++) {
                String meal = queue.take();
                System.out.printf("[%s] took %s%n", Thread.currentThread().getName(), meal);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while consuming", e);
        }
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for blocking queue demo", e);
        }
    }
}