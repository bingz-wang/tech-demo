# Spring AI Alibaba 学习模块

这个模块用于学习 `Spring AI Alibaba` 的基础接入方式，重点先放在最容易上手的部分：`DashScope starter + ChatClient + profile 切换`。

## 你能学到什么

- Spring AI Alibaba 和 Spring AI 的关系
- `spring-ai-alibaba-starter-dashscope` 的基本接入方式
- 如何用 `ChatClient` 写一个最小问答接口
- 如何在本地学习模式和真实 DashScope 模式之间切换

## 模块结构

- `controller`：暴露学习接口
- `service`：封装学习模式和真实调用模式
- `config`：根据上下文是否存在 `ChatClient` 来选择 stub 或真实实现
- `application.yml`：默认关闭真实模型，保证第一次启动可用
- `application-dashscope.yml`：启用 DashScope 模式

## 启动方式

### 本地学习模式

```bash
mvn -pl springai-alibaba-demo spring-boot:run
```

启动后可访问：

- `GET http://localhost:8082/api/springai-alibaba/guide`
- `POST http://localhost:8082/api/springai-alibaba/chat`
- `POST http://localhost:8082/api/springai-alibaba/compare`

请求示例：

```json
{
  "question": "Spring AI Alibaba 和 Spring AI 是什么关系？"
}
```

### DashScope 真实模型模式

先配置环境变量：

```bash
set AI_DASHSCOPE_API_KEY=你的key
```

再启动：

```bash
mvn -pl springai-alibaba-demo spring-boot:run -Dspring-boot.run.profiles=dashscope
```

## 建议学习顺序

1. 先对照 `springai-demo` 看两个模块的相同结构
2. 再看 `SpringAiAlibabaStudyConfig`，理解为什么这里仍然是 `ChatClient`
3. 再看 `application-dashscope.yml`，理解提供方配置如何切换
4. 最后继续扩展到 Agent Framework 或 Graph 场景
