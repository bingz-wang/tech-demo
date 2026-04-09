package com.wbz.multithreading.synchronization;

import java.util.concurrent.locks.StampedLock;

/**
 * 学习主题：StampedLock 乐观读
 * 核心知识：体验写锁、悲观读锁和乐观读校验的组合用法，理解其适合读多写少场景。
 * 运行说明：运行后可观察坐标更新与距离读取的过程，重点关注 validate 的回退逻辑。
 */
public final class StampedLockCase {

    private StampedLockCase() {
    }

    public static void run() {
        Point point = new Point();
        point.move(3, 4);
        System.out.println("distance squared = " + point.distanceFromOriginSquared());
        point.move(1, 2);
        System.out.println("distance squared again = " + point.distanceFromOriginSquared());
    }

    private static final class Point {
        private final StampedLock lock = new StampedLock();
        private int x;
        private int y;

        private void move(int deltaX, int deltaY) {
            long stamp = lock.writeLock();
            try {
                x += deltaX;
                y += deltaY;
                System.out.printf("point moved to (%d, %d)%n", x, y);
            } finally {
                lock.unlockWrite(stamp);
            }
        }

        private int distanceFromOriginSquared() {
            long stamp = lock.tryOptimisticRead();
            int currentX = x;
            int currentY = y;
            if (!lock.validate(stamp)) {
                stamp = lock.readLock();
                try {
                    currentX = x;
                    currentY = y;
                } finally {
                    lock.unlockRead(stamp);
                }
            }
            return currentX * currentX + currentY * currentY;
        }
    }
}