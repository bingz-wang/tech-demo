package com.wbz.multithreading.cases;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环屏障示例
 * 演示如何使用CyclicBarrier让多个线程在某个屏障点等待彼此
 * CyclicBarrier可以让一组线程互相等待，直到所有线程都到达屏障点
 */
public final class CyclicBarrierCase {

    private CyclicBarrierCase() {
    }

    /**
     * 运行循环屏障示例
     * 演示3个选手等待彼此准备完毕后再开始比赛
     */
    public static void run() {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("所有选手准备完毕，比赛开始"));
        Thread[] players = new Thread[3];

        for (int i = 0; i < players.length; i++) {
            int playerId = i + 1;
            players[i] = new Thread(() -> awaitBarrier(barrier, playerId), "player-" + playerId);
            players[i].start();
        }

        for (Thread player : players) {
            join(player);
        }
    }

    /**
     * 等待屏障的辅助方法
     * @param barrier 循环屏障
     * @param playerId 选手ID
     */
    private static void awaitBarrier(CyclicBarrier barrier, int playerId) {
        try {
            System.out.printf("[%s] 第 %d 位选手已就位%n", Thread.currentThread().getName(), playerId);
            Thread.sleep(50L * playerId);
            barrier.await();
            System.out.printf("[%s] 开始冲刺%n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("CyclicBarrier 示例被中断", e);
        } catch (BrokenBarrierException e) {
            throw new IllegalStateException("屏障已损坏", e);
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
            throw new IllegalStateException("等待 CyclicBarrier 示例线程结束时被中断", e);
        }
    }
}
