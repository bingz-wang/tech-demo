package com.wbz.multithreading.async;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 学习主题：ForkJoinPool 分治计算
 * 核心知识：通过递归拆分任务并汇总结果，理解 fork、compute、join 的配合方式。
 * 运行说明：运行后可观察区间求和如何被逐步拆解并最终合并。
 */
public final class ForkJoinCase {

    private ForkJoinCase() {
    }

    public static void run() {
        try (ForkJoinPool pool = new ForkJoinPool()) {
            int result = pool.invoke(new SumTask(1, 10));
            System.out.println("ForkJoin sum result = " + result);
        }
    }

    private static final class SumTask extends RecursiveTask<Integer> {
        private static final int THRESHOLD = 3;

        private final int start;
        private final int end;

        private SumTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

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