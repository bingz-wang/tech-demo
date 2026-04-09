package com.wbz.multithreading.cases;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

/**
 * 线程协作示例
 * 演示如何使用wait/notify和CountDownLatch实现线程间协作
 * 包括生产者-消费者模式和有界队列的实现
 */
public final class CooperationCase {

    private CooperationCase() {
    }

    /**
     * 运行线程协作示例
     * 演示生产者和消费者线程如何协作处理有界队列
     */
    public static void run() {
        BoundedQueue queue = new BoundedQueue(2);
        CountDownLatch latch = new CountDownLatch(2);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    queue.put("task-" + i);
                }
            } finally {
                latch.countDown();
            }
        }, "producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    String item = queue.take();
                    System.out.printf("[%s] 处理 %s%n", Thread.currentThread().getName(), item);
                }
            } finally {
                latch.countDown();
            }
        }, "consumer");

        producer.start();
        consumer.start();
        await(latch);
        System.out.println("线程协作示例结束");
    }

    /**
     * 等待CountDownLatch完成的辅助方法
     * @param latch CountDownLatch实例
     */
    private static void await(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("等待线程协作示例结束时被中断", e);
        }
    }

    /**
     * 有界队列类，使用wait/notify实现线程安全的生产和消费
     */
    private static final class BoundedQueue {
        /** 队列容器 */
        private final Queue<String> queue = new LinkedList<>();
        /** 队列容量 */
        private final int capacity;

        /**
         * 构造函数
         * @param capacity 队列容量
         */
        private BoundedQueue(int capacity) {
            this.capacity = capacity;
        }

        /**
         * 向队列中添加元素，队列满时阻塞等待
         * 使用wait/notify实现线程间的协作
         * @param value 要添加的元素
         */
        public synchronized void put(String value) {
            try {
                while (queue.size() == capacity) {
                    wait();
                }
                queue.offer(value);
                System.out.printf("[%s] 生产 %s%n", Thread.currentThread().getName(), value);
                notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("生产数据时被中断", e);
            }
        }

        /**
         * 从队列中取出元素，队列空时阻塞等待
         * 使用wait/notify实现线程间的协作
         * @return 取出的元素
         */
        public synchronized String take() {
            try {
                while (queue.isEmpty()) {
                    wait();
                }
                String value = queue.poll();
                notifyAll();
                return value;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("消费数据时被中断", e);
            }
        }
    }
}
