package com.wbz.multithreading.coordination;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 学习主题：CyclicBarrier 屏障同步
 * 核心知识：让多个线程在同一个阶段点汇合，再同时进入下一步。
 * 运行说明：运行后可观察多个 player 在 barrier 前等待，全部到齐后再继续执行。
 */
public final class CyclicBarrierCase {

    private CyclicBarrierCase() {
    }

    public static void run() {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("all players are ready"));
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

    private static void awaitBarrier(CyclicBarrier barrier, int playerId) {
        try {
            System.out.printf("[%s] player %d is getting ready%n", Thread.currentThread().getName(), playerId);
            Thread.sleep(40L * playerId);
            barrier.await();
            System.out.printf("[%s] player %d started%n", Thread.currentThread().getName(), playerId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted during CyclicBarrier demo", e);
        } catch (BrokenBarrierException e) {
            throw new IllegalStateException("Barrier was broken", e);
        }
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for CyclicBarrier demo", e);
        }
    }
}