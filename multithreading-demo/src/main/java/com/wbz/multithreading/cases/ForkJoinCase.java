package com.wbz.multithreading.cases;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinPool示例
 * 演示如何使用ForkJoinPool进行分治计算
 * ForkJoinPool采用工作窃取算法，适合CPU密集型任务的并行处理
 */
public final class ForkJoinCase {

    private ForkJoinCase() {
    }

    /**
     * 运行ForkJoinPool示例
     * 演示使用分治算法计算1到10的和
     */
    public static void run() {
        try (ForkJoinPool pool = new ForkJoinPool()) {
            int result = pool.invoke(new SumTask(1, 10));
            System.out.println("ForkJoin 求和结果：" + result);
        }
    }

    /**
     * 求和任务类，继承RecursiveTask实现分治求和
     */
    private static final class SumTask extends RecursiveTask<Integer> {
        /** 任务阈值，小于等于此值直接计算 */
        private static final int THRESHOLD = 3;

        /** 起始值 */
        private final int start;
        /** 结束值 */
        private final int end;

        /**
         * 构造函数
         * @param start 起始值
         * @param end 结束值
         */
        private SumTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        /**
         * 计算方法，实现分治逻辑
         * 如果任务足够小则直接计算，否则拆分任务
         * @return 求和结果
         */
        @Override
        protected Integer compute() {
            if (end - start <= THRESHOLD) {
                int sum = 0;
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
                return sum;
            }

            int middle = (start + end) / 2;
            SumTask left = new SumTask(start, middle);
            SumTask right = new SumTask(middle + 1, end);
            left.fork();
            int rightResult = right.compute();
            int leftResult = left.join();
            return leftResult + rightResult;
        }
    }
}
