package com.wbz.multithreading.cases;

import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock示例
 * 演示如何使用StampedLock实现乐观读锁
 * StampedLock是Java 8引入的锁，支持三种模式：写锁、悲观读锁、乐观读
 */
public final class StampedLockCase {

    private StampedLockCase() {
    }

    /**
     * 运行StampedLock示例
     * 演示使用乐观读锁读取坐标，写锁更新坐标
     */
    public static void run() {
        Point point = new Point();
        point.move(3, 4);
        System.out.println("乐观读距离平方：" + point.distanceFromOriginSquared());
        point.move(1, 2);
        System.out.println("再次读取距离平方：" + point.distanceFromOriginSquared());
    }

    /**
     * 坐标点类，使用StampedLock保护坐标数据
     */
    private static final class Point {
        /** StampedLock实例 */
        private final StampedLock lock = new StampedLock();
        /** X坐标 */
        private int x;
        /** Y坐标 */
        private int y;

        /**
         * 移动坐标，使用写锁保证线程安全
         * @param deltaX X方向偏移量
         * @param deltaY Y方向偏移量
         */
        public void move(int deltaX, int deltaY) {
            long stamp = lock.writeLock();
            try {
                x += deltaX;
                y += deltaY;
                System.out.printf("写入坐标后位置为 (%d, %d)%n", x, y);
            } finally {
                lock.unlockWrite(stamp);
            }
        }

        /**
         * 计算距离原点的平方，使用乐观读锁
         * 先尝试乐观读，如果验证失败则升级为悲观读锁
         * @return 距离原点的平方
         */
        public int distanceFromOriginSquared() {
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
