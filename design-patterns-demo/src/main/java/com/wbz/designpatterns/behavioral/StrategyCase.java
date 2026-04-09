package com.wbz.designpatterns.behavioral;

/**
 * 策略模式演示
 * 策略模式定义了一系列算法，并将每一个算法封装起来，使它们可以相互替换
 * 策略模式让算法独立于使用它的客户而变化
 * 适用场景：需要在运行时选择算法、有多个相似的类仅在行为上有所不同等
 */
public final class StrategyCase {

    private StrategyCase() {
    }

    /**
     * 运行策略模式示例
     * 演示如何使用不同折扣策略计算价格
     */
    public static void run() {
        PaymentContext discountContext = new PaymentContext(new VipDiscountStrategy());
        PaymentContext couponContext = new PaymentContext(new CouponDiscountStrategy());

        System.out.println("VIP 折后价：" + discountContext.checkout(100));
        System.out.println("优惠券折后价：" + couponContext.checkout(100));
    }

    /**
     * 折扣策略接口，定义了应用折扣的方法
     */
    private interface DiscountStrategy {
        /**
         * 应用折扣算法
         * @param price 原价
         * @return 折后价格
         */
        double apply(double price);
    }

    /**
     * 支付上下文类，持有并使用折扣策略
     */
    private static final class PaymentContext {
        /** 当前使用的折扣策略 */
        private final DiscountStrategy strategy;

        /**
         * 构造函数，注入折扣策略
         * @param strategy 折扣策略实例
         */
        private PaymentContext(DiscountStrategy strategy) {
            this.strategy = strategy;
        }

        /**
         * 使用当前策略计算结账价格
         * @param price 原价
         * @return 折后价格
         */
        public double checkout(double price) {
            return strategy.apply(price);
        }
    }

    /**
     * VIP折扣策略：打8折
     */
    /**
     * VIP折扣策略：打8折
     */
    private static final class VipDiscountStrategy implements DiscountStrategy {
        /**
         * 应用VIP折扣（8折）
         * @param price 原价
         * @return 折后价格
         */
        @Override
        public double apply(double price) {
            return price * 0.8;
        }
    }

    /**
     * 优惠券折扣策略：满减15元
     */
    /**
     * 优惠券折扣策略：满减15元
     */
    private static final class CouponDiscountStrategy implements DiscountStrategy {
        /**
         * 应用优惠券折扣（减15元，最低为0）
         * @param price 原价
         * @return 折后价格
         */
        @Override
        public double apply(double price) {
            return Math.max(0, price - 15);
        }
    }
}
