package com.wbz.multithreading.coordination;

import java.util.concurrent.Phaser;

/**
 * 学习主题：Phaser 分阶段协作
 * 核心知识：支持多阶段同步，并允许参与方动态注册与注销。
 * 运行说明：运行后可观察多个 worker 在两个阶段中反复汇合，适合理解多轮协作场景。
 */
public final class PhaserCase {

    private PhaserCase() {
    }

    public static void run() {
        Phaser phaser = new Phaser(1);
        for (int i = 1; i <= 3; i++) {
            int workerId = i;
            phaser.register();
            Thread.startVirtualThread(() -> runPhaseTask(phaser, workerId));
        }

        for (int phase = 0; phase < 2; phase++) {
            int current = phaser.arriveAndAwaitAdvance();
            System.out.println("main observed phase " + current + " completed");
        }
        phaser.arriveAndDeregister();
    }

    private static void runPhaseTask(Phaser phaser, int workerId) {
        try {
            for (int phase = 0; phase < 2; phase++) {
                System.out.printf("[%s] worker %d phase %d%n", Thread.currentThread(), workerId, phase);
                Thread.sleep(25L * workerId);
                phaser.arriveAndAwaitAdvance();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted during Phaser demo", e);
        } finally {
            phaser.arriveAndDeregister();
        }
    }
}