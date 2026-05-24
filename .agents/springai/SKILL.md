---
name: springai-demo
description: Spring AI + OpenAI 学习模块 — ChatClient 调用链、本地存根学习模式。
---

# springai-demo 开发指南

## 模块信息

- **包名**: `com.wbz.springai`
- **类型**: Spring Boot Web（端口 **8081**）
- **启动**: `mvn -pl springai-demo spring-boot:run`

## 代码结构

```
springai-demo/src/main/java/com/wbz/springai/
├── SpringAiDemoApplication.java
├── config/
│   └── SpringAiStudyConfig.java    # 学习模式 / OpenAI 模式切换
├── controller/
│   └── SpringAiLearningController.java
├── model/
│   ├── ChatRequest.java            # DTO — 使用 record
│   ├── ChatResponse.java
│   └── LearningGuideResponse.java
└── service/
    ├── AiStudyService.java         # 接口
    ├── LocalStubAiStudyService.java      # 本地存根（无需 API Key）
    └── OpenAiChatClientAiStudyService.java # OpenAI 真实调用
```

## Profile 切换

| Profile | Service 实现 | 说明 |
|----------|-------------|------|
| default | `LocalStubAiStudyService` | 本地模拟，无需 Key |
| `openai` | `OpenAiChatClientAiStudyService` | 真实 OpenAI API |

```bash
# 本地模式
mvn -pl springai-demo spring-boot:run

# OpenAI 模式
mvn -pl springai-demo spring-boot:run -Dspring-boot.run.profiles=openai
```

## API

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/springai/guide` | 学习路线指引 |
| POST | `/api/springai/chat` | 聊天对话 |
| POST | `/api/springai/prompt/teacher` | 教师模式提问 |
