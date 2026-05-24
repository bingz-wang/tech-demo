---
name: multithreading-demo
description: Java 多线程与并发编程学习模块 — 线程基础、同步锁、协作、异步、虚拟线程。
---

# multithreading-demo 开发指南

## 模块信息

- **包名**: `com.wbz.multithreading`
- **类型**: 控制台程序（IDE 运行 Main.java）
- **入口**: `src/main/java/com/wbz/multithreading/Main.java`

## 代码结构

```
multithreading-demo/src/main/java/com/wbz/multithreading/
├── Main.java                    # 入口，运行所有案例
├── PackageGuide.java            # 包导航说明
├── foundation/                  # 线程基础
│   ├── BasicThreadCase.java     # Thread / Runnable 创建
│   ├── FutureAndCallableCase.java # Future + Callable
│   └── ThreadLocalCase.java     # ThreadLocal 使用
├── synchronization/             # 同步与锁
│   ├── SynchronizedCase.java    # synchronized 关键字
│   ├── LockCase.java            # ReentrantLock
│   ├── StampedLockCase.java     # StampedLock 读写锁
│   └── DeadlockCase.java        # 死锁演示与排查
├── coordination/                # 线程协作
│   ├── CooperationCase.java     # wait/notify
│   ├── BlockingQueueCase.java   # BlockingQueue
│   ├── SemaphoreCase.java       # Semaphore
│   ├── CyclicBarrierCase.java   # CyclicBarrier
│   └── PhaserCase.java          # Phaser
├── async/                       # 异步编程
│   ├── CompletableFutureCase.java
│   ├── ExecutorServiceCase.java
│   └── ForkJoinCase.java
└── loom/                        # 虚拟线程
    └── VirtualThreadCase.java   # Java 21+ VirtualThread
```

## 新增案例指南

1. 在对应子包下新建 `XxxCase.java`
2. 类命名: 技术名 + `Case`
3. 在 Main.java 中调用新案例的入口方法
4. 控制台输出清晰的演示效果
