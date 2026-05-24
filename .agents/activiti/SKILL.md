---
name: activiti-demo
description: Activiti/Flowable 工作流学习模块 — BPMN 2.0 流程定义、任务管理、审批流。
---

# activiti-demo 开发指南

## 模块信息

- **包名**: `com.wbz.flowable`
- **类型**: Spring Boot Web（端口 **8083**）
- **启动**: `mvn -pl activiti-demo spring-boot:run`
- **数据库**: PostgreSQL `localhost:5432/postgres`（`postgres/123456`）

## 代码结构

```
activiti-demo/src/main/java/com/wbz/activiti/
├── ActivitiDemoApplication.java
├── config/
│   └── ActivitiConfig.java              # 启动时打印已部署流程
├── controller/
│   └── LeaveController.java             # 请假流程 REST API
└── service/
    └── LeaveService.java                # RuntimeService / TaskService 封装

src/main/resources/
├── application.yml
└── processes/
    └── leave-process.bpmn20.xml         # BPMN 2.0 流程定义
```

## 核心 API

| Flowable Service | 用途 |
|-----------------|------|
| `RuntimeService` | 启动流程实例、查询运行中实例 |
| `TaskService` | 查询/完成任务 |
| `HistoryService` | 查询历史流程和变量 |
| `RepositoryService` | 部署/查询流程定义 |

## 请假流程逻辑

```
提交申请 → 经理审批 → {days > 3?} → HR审批 → 结束
                        ↓
                     直接结束(≤3天 / 拒绝)
```

## 新增 BPMN 流程

1. 在 `processes/` 下新增 `.bpmn20.xml` 文件
2. 确保 `flowable:assignee="${变量名}"` 用于动态分配任务人
3. 条件表达式使用 `${}` UEL 语法
4. 重启应用自动部署（或调用 `RepositoryService.createDeployment()` 手动部署）
