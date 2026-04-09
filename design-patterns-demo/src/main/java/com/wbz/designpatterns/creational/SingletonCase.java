package com.wbz.designpatterns.creational;

/**
 * 单例模式演示
 * 单例模式确保一个类只有一个实例，并提供全局访问点
 * 适用场景：配置管理、日志记录、数据库连接池等
 */
public final class SingletonCase {

    private SingletonCase() {
    }

    /**
     * 运行单例模式示例
     * 验证两次获取的是同一个实例
     */
    public static void run() {
        AppConfig first = AppConfig.getInstance();
        AppConfig second = AppConfig.getInstance();

        System.out.println("是否拿到同一个实例：" + (first == second));
        System.out.println("应用名：" + first.applicationName());
    }

    /**
     * 应用配置类，使用静态内部类实现单例模式
     * 线程安全且延迟加载
     */
    private static final class AppConfig {
        /** 单例实例，在类加载时创建 */
        private static final AppConfig INSTANCE = new AppConfig("tech-demo");

        /** 应用名称 */
        private final String applicationName;

        /**
         * 私有构造函数，防止外部实例化
         * @param applicationName 应用名称
         */
        private AppConfig(String applicationName) {
            this.applicationName = applicationName;
        }

        /**
         * 获取单例实例的全局访问点
         * @return AppConfig 单例实例
         */
        public static AppConfig getInstance() {
            return INSTANCE;
        }

        /**
         * 获取应用名称
         * @return 应用名称
         */
        public String applicationName() {
            return applicationName;
        }
    }
}
