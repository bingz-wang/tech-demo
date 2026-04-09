package com.wbz.designpatterns.structural;

/**
 * 适配器模式演示
 * 适配器模式将一个类的接口转换成客户希望的另一个接口
 * 适配器使得原本由于接口不兼容而不能一起工作的类可以一起工作
 * 适用场景：使用第三方库但其接口与系统不兼容、需要复用现有类但其接口不符合要求等
 */
public final class AdapterCase {

    private AdapterCase() {
    }

    /**
     * 运行适配器模式示例
     * 演示如何使用适配器将旧支付SDK集成到新支付接口中
     */
    public static void run() {
        PaymentProcessor processor = new LegacyPaymentAdapter(new LegacyPaymentSdk());
        System.out.println(processor.pay(99.9));
    }

    /**
     * 目标接口：新支付系统使用的标准支付处理器接口
     */
    private interface PaymentProcessor {
        /**
         * 执行支付操作
         * @param amount 支付金额（元）
         * @return 支付结果信息
         */
        String pay(double amount);
    }

    /**
     * 被适配者：旧的支付SDK，使用分为单位且方法名不同
     */
    private static final class LegacyPaymentSdk {
        /**
         * 旧SDK的支付方法，接收分为单位
         * @param amountInFen 支付金额（分）
         * @return 支付结果信息
         */
        public String makePaymentInFen(long amountInFen) {
            return "旧支付 SDK 已处理：" + amountInFen + " 分";
        }
    }

    /**
     * 适配器类：将旧支付SDK适配到新支付接口
     * 负责单位转换（元转分）和方法调用转换
     */
    private static final class LegacyPaymentAdapter implements PaymentProcessor {
        /** 被适配的旧支付SDK实例 */
        private final LegacyPaymentSdk sdk;

        /**
         * 构造函数，注入需要适配的旧SDK
         * @param sdk 旧支付SDK实例
         */
        private LegacyPaymentAdapter(LegacyPaymentSdk sdk) {
            this.sdk = sdk;
        }

        /**
         * 实现新接口，内部调用旧SDK并完成单位转换
         * @param amount 支付金额（元）
         * @return 支付结果信息
         */
        @Override
        public String pay(double amount) {
            return sdk.makePaymentInFen(Math.round(amount * 100));
        }
    }
}
