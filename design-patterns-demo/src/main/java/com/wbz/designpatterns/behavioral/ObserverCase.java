package com.wbz.designpatterns.behavioral;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式演示
 * 观察者模式定义了对象之间的一对多依赖关系，当一个对象改变状态时，它的所有依赖者都会收到通知并自动更新
 * 适用场景：事件处理系统、消息订阅系统、模型-视图分离等
 */
public final class ObserverCase {

    private ObserverCase() {
    }

    /**
     * 运行观察者模式示例
     * 演示如何使用观察者模式实现股票通知系统
     */
    public static void run() {
        StockSubject subject = new StockSubject();
        subject.addObserver(message -> System.out.println("短信通知：" + message));
        subject.addObserver(message -> System.out.println("邮件通知：" + message));

        subject.publish("Java 设计模式课程已更新");
    }

    /**
     * 观察者接口，定义了接收消息的方法
     * 使用函数式接口，可以用Lambda表达式实现
     */
    @FunctionalInterface
    private interface Observer {
        /**
         * 接收到消息时的回调方法
         * @param message 消息内容
         */
        void onMessage(String message);
    }

    /**
     * 主题类（被观察者），维护观察者列表并发送通知
     */
    private static final class StockSubject {
        /** 观察者列表 */
        private final List<Observer> observers = new ArrayList<>();

        /**
         * 添加观察者
         * @param observer 要添加的观察者
         */
        public void addObserver(Observer observer) {
            observers.add(observer);
        }

        /**
         * 发布消息，通知所有观察者
         * @param message 要发布的消息
         */
        public void publish(String message) {
            observers.forEach(observer -> observer.onMessage(message));
        }
    }
}
