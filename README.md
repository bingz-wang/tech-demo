# tech-demo

个人技术学习与实践仓库，包含多模块 Java Demo，覆盖：

- Java 并发编程（基础到进阶）
- 常见设计模式实践
- Spring AI（OpenAI / DashScope）接入学习
- Spring Security 认证与授权学习
- Activiti / Flowable 工作流学习

---

## 项目结构

```text
tech-demo
├── multithreading-demo         # Java 并发与多线程案例
├── design-patterns-demo        # 设计模式案例
├── springai-demo               # Spring AI + OpenAI 学习模块
├── springai-alibaba-demo       # Spring AI Alibaba + DashScope 学习模块
├── spring-security-demo        # Spring Security 认证授权学习
├── other-demo                  # 其他工具类（Jasypt 加密等）
└── activiti-demo               # Activiti/Flowable 工作流学习
```

根工程为 Maven 聚合项目（`packaging=pom`），统一管理以上子模块。

---

## 环境要求

- JDK: `25`
- Maven: `3.9+`（建议）
- PostgreSQL: `16+`（activiti-demo 需要）
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

| 模块 | 端口 | 启动命令 |
|------|------|----------|
| spring-security-demo | 8080 | `mvn -pl spring-security-demo spring-boot:run` |
| springai-demo | 8081 | `mvn -pl springai-demo spring-boot:run` |
| springai-alibaba-demo | 8082 | `mvn -pl springai-alibaba-demo spring-boot:run` |
| activiti-demo | 8083 | `mvn -pl activiti-demo spring-boot:run` |

#### A. Spring Security 模块（端口 `8080`）

纯 REST 接口，展示认证与授权：

```bash
mvn -pl spring-security-demo spring-boot:run
```

可用接口：

- `GET /` — 首页
- `GET /public/info` — 公开接口
- `GET /user/me` — 需 USER 角色
- `GET /admin/dashboard` — 需 ADMIN 角色

#### B. Spring AI 模块（端口 `8081`）

```bash
mvn -pl springai-demo spring-boot:run
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

#### C. Spring AI Alibaba 模块（端口 `8082`）

```bash
mvn -pl springai-alibaba-demo spring-boot:run
```

可用接口：

- `GET /api/springai-alibaba/guide`
- `POST /api/springai-alibaba/chat`
- `POST /api/springai-alibaba/compare`

#### D. Activiti 工作流模块（端口 `8083`）

> 需要本地 PostgreSQL 运行中（`localhost:5432`，账号 `postgres/123456`）

```bash
mvn -pl activiti-demo spring-boot:run
```

**学习流程：**

```bash
# 1. 提交请假申请
curl -X POST http://localhost:8083/api/leave/submit \
  -H "Content-Type: application/json" \
  -d '{"employee":"zhangsan","manager":"lisi","hr":"wangwu","days":5,"reason":"年假"}'

# 2. 查询经理待办
curl "http://localhost:8083/api/leave/tasks?assignee=lisi"

# 3. 经理审批通过 (taskId 替换为上一步返回的 id)
curl -X POST http://localhost:8083/api/leave/complete/{taskId} \
  -H "Content-Type: application/json" \
  -d '{"approved":true,"comment":"同意"}'

# 4. 查询 HR 待办
curl "http://localhost:8083/api/leave/tasks?assignee=wangwu"

# 5. HR 审批
curl -X POST http://localhost:8083/api/leave/complete/{taskId} \
  -H "Content-Type: application/json" \
  -d '{"approved":true,"comment":"批准"}'
```

可用接口：

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/leave/submit` | 提交请假申请 |
| GET | `/api/leave/tasks?assignee=xxx` | 查待办任务 |
| POST | `/api/leave/complete/{taskId}` | 完成审批 |
| GET | `/api/leave/instances` | 运行中的流程实例 |
| GET | `/api/leave/history` | 历史流程实例 |

> Flowable 是 Activiti 核心团队在原 Activiti 6 基础上创建的分支，两者共享相同的 BPMN 2.0 引擎和 API 设计理念。本项目使用 Flowable 以确保 Spring Boot 3.x 兼容性。

#### E. 多线程与设计模式模块

这两个模块是控制台示例工程，推荐在 IDE 中直接运行对应 `Main` 类：

- `multithreading-demo/src/main/java/com/wbz/multithreading/Main.java`
- `design-patterns-demo/src/main/java/com/wbz/designpatterns/Main.java`

---

## 模块学习建议

1. `multithreading-demo` — 先建立并发基础：线程、锁、协作、线程池、CompletableFuture、虚拟线程
2. `design-patterns-demo` — 巩固设计抽象能力：创建型、结构型、行为型模式
3. `spring-security-demo` — 理解认证授权流程：Filter Chain、UserDetails、角色权限
4. `springai-demo` — 学习 Spring AI 标准接入方式与 ChatClient 基本调用链
5. `springai-alibaba-demo` — 对比 Spring AI 与 Alibaba 生态的接入差异
6. `activiti-demo` — 学习 BPMN 2.0 工作流：流程定义、用户任务、网关、审批流

---

## AI 编程协作

本项目包含 AI 编程助手相关配置，详见：

- [`agents.md`](AGENTS.md) — 项目上下文与编码规范
- [`.agents/`](./.agents/) — AI 工作流模板与规则

---

## 常用命令

```bash
# 编译所有模块
mvn -q -DskipTests compile

# 测试指定模块
mvn -pl springai-demo -am test
mvn -pl activiti-demo -am test

# 运行指定模块
mvn -pl activiti-demo spring-boot:run
```

---

## License

[MIT](./LICENSE)
