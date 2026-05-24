---
name: design-patterns-demo
description: 设计模式学习模块 — 创建型、结构型、行为型三大类模式实践。
---

# design-patterns-demo 开发指南

## 模块信息

- **包名**: `com.wbz.designpatterns`
- **类型**: 控制台程序（IDE 运行 Main.java）
- **入口**: `src/main/java/com/wbz/designpatterns/Main.java`

## 代码结构

```
design-patterns-demo/src/main/java/com/wbz/designpatterns/
├── Main.java                    # 入口，运行所有案例
├── creational/                  # 创建型模式
│   ├── SingletonCase.java       # 单例（饿汉/懒汉/枚举）
│   ├── FactoryMethodCase.java   # 工厂方法
│   └── BuilderCase.java         # 建造者
├── structural/                  # 结构型模式
│   ├── ProxyCase.java           # 代理（静态/动态/CGLIB）
│   └── AdapterCase.java         # 适配器
└── behavioral/                  # 行为型模式
    ├── StrategyCase.java        # 策略
    ├── ObserverCase.java        # 观察者
    ├── CommandCase.java         # 命令
    └── TemplateMethodCase.java  # 模板方法
```

## 新增模式指南

1. 按类型放入 `creational/` / `structural/` / `behavioral/`
2. 类命名: 模式名 + `Case`
3. 案例中展示使用场景 + 对比不用模式的区别
4. 在 Main.java 中添加调用
