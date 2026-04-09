package com.wbz.designpatterns;

import com.wbz.designpatterns.behavioral.CommandCase;
import com.wbz.designpatterns.behavioral.TemplateMethodCase;
import com.wbz.designpatterns.creational.BuilderCase;
import com.wbz.designpatterns.creational.FactoryMethodCase;
import com.wbz.designpatterns.creational.SingletonCase;
import com.wbz.designpatterns.structural.AdapterCase;
import com.wbz.designpatterns.structural.ProxyCase;
import com.wbz.designpatterns.behavioral.ObserverCase;
import com.wbz.designpatterns.behavioral.StrategyCase;

/**
 * 设计模式演示程序主入口
 * 展示了9种常见的设计模式实现，包括：
 * - 创建型模式：单例模式、建造者模式、工厂方法模式
 * - 结构型模式：适配器模式、代理模式
 * - 行为型模式：策略模式、模板方法模式、命令模式、观察者模式
 */
public class Main {

    /**
     * 程序入口点，依次运行所有设计模式示例
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        run("1. 单例模式", SingletonCase::run);
        run("2. 建造者模式", BuilderCase::run);
        run("3. 工厂方法模式", FactoryMethodCase::run);
        run("4. 适配器模式", AdapterCase::run);
        run("5. 代理模式", ProxyCase::run);
        run("6. 策略模式", StrategyCase::run);
        run("7. 模板方法模式", TemplateMethodCase::run);
        run("8. 命令模式", CommandCase::run);
        run("9. 观察者模式", ObserverCase::run);
    }

    /**
     * 运行单个设计模式示例的辅助方法
     * @param title 示例标题
     * @param action 要执行的操作
     */
    private static void run(String title, Runnable action) {
        System.out.println();
        System.out.println("-".repeat(16) + " " + title + " " + "-".repeat(16));
        action.run();
    }
}
