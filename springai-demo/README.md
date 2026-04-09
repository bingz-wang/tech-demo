# Spring AI 学习模块

这是一个适合入门的 `Spring AI` 学习模块，目标是先把最核心的开发方式跑通，再逐步扩展到更复杂的能力。

## 你能学到什么

- Spring AI 在 Spring Boot 项目中的基本接入方式
- `ChatClient.Builder` 和 `ChatClient` 的典型调用姿势
- `system prompt` 和 `user prompt` 的区别
- 如何通过 profile 切换“本地学习模式”和“真实模型模式”

## 模块结构

- `controller`：暴露学习接口
- `service`：封装 AI 问答逻辑
- `config`：根据运行环境选择真实模型或本地 stub
- `application.yml`：默认关闭真实模型，第一次启动就能成功
- `application-openai.yml`：启用 OpenAI 模式

## 启动方式

### 本地学习模式

不需要 API Key，直接启动：

```bash
mvn -pl springai-demo spring-boot:run
```

启动后可以访问：

- `GET http://localhost:8081/api/springai/guide`
- `POST http://localhost:8081/api/springai/chat`
- `POST http://localhost:8081/api/springai/prompt/teacher`

请求示例：

```json
{
  "question": "什么是 Spring AI 里的 ChatClient？"
}
```

### 真实 OpenAI 模式

先配置环境变量：

```bash
set OPENAI_API_KEY=你的key
```

然后带 `openai` profile 启动：

```bash
mvn -pl springai-demo spring-boot:run -Dspring-boot.run.profiles=openai
```

## 建议学习顺序

1. 先看 `SpringAiLearningController`，理解接口入口长什么样
2. 再看 `AiStudyService`，理解为什么要先抽象一层服务
3. 然后看 `SpringAiStudyConfig`，理解 Spring 如何决定注入真实 AI 还是 stub
4. 最后看 `OpenAiChatClientAiStudyService`，重点理解 `chatClient.prompt().system(...).user(...).call().content()`

## 你下一步可以继续扩展

- 增加结构化输出
- 接入向量库做 RAG
- 增加工具调用
- 补一个前端页面做可视化学习面板
