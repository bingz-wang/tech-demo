# tech-demo

个人技术学习与实践仓库，包含多模块 Java Demo，覆盖：

- Java 并发编程（基础到进阶）
- 常见设计模式实践
- Spring AI（OpenAI）接入学习
- Spring AI Alibaba（DashScope）接入学习

---

## 项目结构

```text
tech-demo
├─ multithreading-demo      # Java 并发与多线程案例
├─ design-patterns-demo     # 设计模式案例
├─ springai-demo            # Spring AI + OpenAI 学习模块
└─ springai-alibaba-demo    # Spring AI Alibaba + DashScope 学习模块
```

根工程为 Maven 聚合项目（`packaging=pom`），统一管理以上子模块。

---

## 环境要求

- JDK: `25`
- Maven: `3.9+`（建议）
- OS: Windows / macOS / Linux

> 当前仓库已验证命令：`mvn -q -DskipTests compile` 可通过。

---

## 快速开始

### 1) 克隆并编译

```bash
git clone <your-repo-url>
cd tech-demo
mvn -q -DskipTests compile
```

### 2) 运行指定模块

#### A. Spring AI 模块（端口 `8081`）

本地学习模式（无需 API Key）：

```bash
mvn -pl springai-demo spring-boot:run
```

OpenAI 模式（需设置 `OPENAI_API_KEY`）：

```bash
set OPENAI_API_KEY=你的key
mvn -pl springai-demo spring-boot:run -Dspring-boot.run.profiles=openai
```

可用接口：

- `GET /api/springai/guide`
- `POST /api/springai/chat`
- `POST /api/springai/prompt/teacher`

示例请求体：

```json
{
  "question": "什么是 Spring AI 的 ChatClient？"
}
```

#### B. Spring AI Alibaba 模块（端口 `8082`）

本地学习模式（无需 API Key）：

```bash
mvn -pl springai-alibaba-demo spring-boot:run
```

DashScope 模式（需设置 `AI_DASHSCOPE_API_KEY`）：

```bash
set AI_DASHSCOPE_API_KEY=你的key
mvn -pl springai-alibaba-demo spring-boot:run -Dspring-boot.run.profiles=dashscope
```

可用接口：

- `GET /api/springai-alibaba/guide`
- `POST /api/springai-alibaba/chat`
- `POST /api/springai-alibaba/compare`

示例请求体：

```json
{
  "question": "Spring AI Alibaba 和 Spring AI 的关系是什么？"
}
```

#### C. 多线程与设计模式模块

这两个模块是控制台示例工程，推荐在 IDE 中直接运行对应 `Main` 类：

- `multithreading-demo/src/main/java/com/wbz/multithreading/Main.java`
- `design-patterns-demo/src/main/java/com/wbz/designpatterns/Main.java`

---

## 模块学习建议

1. `multithreading-demo`  
   先建立并发基础：线程、锁、协作、线程池、CompletableFuture、虚拟线程。
2. `design-patterns-demo`  
   巩固设计抽象能力：创建型、结构型、行为型模式。
3. `springai-demo`  
   学习 Spring AI 标准接入方式与 `ChatClient` 基本调用链。
4. `springai-alibaba-demo`  
   对比 Spring AI 与 Alibaba 生态的接入差异与配置方式。

---

## 常用命令

```bash
# 编译所有模块
mvn -q -DskipTests compile

# 测试指定模块
mvn -pl springai-demo -am test
mvn -pl springai-alibaba-demo -am test
```

---

## License

[MIT](./LICENSE)
