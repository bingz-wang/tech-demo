---
name: springai-alibaba-demo
description: Spring AI Alibaba + DashScope 学习模块 — 对比 OpenAI 接入差异。
---

# springai-alibaba-demo 开发指南

## 模块信息

- **包名**: `com.wbz.springaialibaba`
- **类型**: Spring Boot Web（端口 **8082**）
- **启动**: `mvn -pl springai-alibaba-demo spring-boot:run`

## 代码结构

```
springai-alibaba-demo/src/main/java/com/wbz/springaialibaba/
├── SpringAiAlibabaDemoApplication.java
├── config/
│   └── SpringAiAlibabaStudyConfig.java
├── controller/
│   └── SpringAiAlibabaLearningController.java
├── model/
│   ├── AlibabaChatRequest.java
│   ├── AlibabaChatResponse.java
│   └── AlibabaLearningGuideResponse.java
└── service/
    ├── AlibabaAiStudyService.java
    ├── LocalStubAlibabaAiStudyService.java
    └── DashScopeChatClientAlibabaAiStudyService.java
```

## Profile 切换

| Profile | Service 实现 | 说明 |
|----------|-------------|------|
| default | `LocalStubAlibabaAiStudyService` | 本地模拟 |
| `dashscope` | `DashScopeChatClientAlibabaAiStudyService` | DashScope API |

## 与 springai-demo 的结构对比

两个模块结构镜像，学习时可对比：
- OpenAI `ChatClient` vs DashScope `ChatClient` 的 Builder 差异
- Prompt Template 配置方式的差异
- API Key 环境变量命名差异
