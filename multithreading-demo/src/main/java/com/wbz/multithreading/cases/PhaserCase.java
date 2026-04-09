package com.wbz.multithreading.cases;

import java.util.concurrent.Phaser;

/**
 * 阶段器示例
 * 演示如何使用Phaser分阶段协调多个线程
 * Phaser比CyclicBarrier更灵活，支持动态注册和多个阶段
 */
public final class PhaserCase {

    private PhaserCase() {
    }

    /**
     * 运行阶段器示例
     * 演示3个学生分阶段完成考试（选择题、简答题、交卷）
     */
    public static void run() {
        Phaser phaser = new Phaser(1);
        Thread[] students = new Thread[3];

        for (int i = 0; i < students.length; i++) {
            int studentId = i + 1;
            phaser.register();
            students[i] = new Thread(() -> exam(phaser, studentId), "student-" + studentId);
            students[i].start();
        }

        phaser.arriveAndDeregister();

        for (Thread student : students) {
            join(student);
        }
    }

    /**
     * 考试流程，分阶段完成不同题型
     * @param phaser 阶段器
     * @param studentId 学生ID
     */
    private static void exam(Phaser phaser, int studentId) {
        System.out.printf("[%s] 学生 %d 完成选择题%n", Thread.currentThread().getName(), studentId);
        phaser.arriveAndAwaitAdvance();

        System.out.printf("[%s] 学生 %d 完成简答题%n", Thread.currentThread().getName(), studentId);
        phaser.arriveAndAwaitAdvance();

        System.out.printf("[%s] 学生 %d 交卷%n", Thread.currentThread().getName(), studentId);
        phaser.arriveAndDeregister();
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
            throw new IllegalStateException("等待 Phaser 示例线程结束时被中断", e);
        }
    }
}
