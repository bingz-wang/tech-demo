# AI 编程协作指南

本文件为 AI 编程助手（Claude Code、Copilot 等）提供项目上下文，帮助更好地理解项目结构、编码规范和常用操作。

---

## 项目概述

- **项目名**: java-demo (技术学习与实践仓库)
- **类型**: Maven 多模块聚合项目
- **Java 版本**: 25
- **Spring Boot 版本**: 3.5.13
- **编码**: UTF-8

## 模块列表

| 模块 | 包名 | 类型 |
|------|------|------|
| `multithreading-demo` | `com.wbz.multithreading` | 控制台 |
| `design-patterns-demo` | `com.wbz.designpatterns` | 控制台 |
| `springai-demo` | `com.wbz.springai` | Web (8081) |
| `springai-alibaba-demo` | `com.wbz.springaialibaba` | Web (8082) |
| `other-demo` | `com.wbz` | 控制台 |
| `spring-security-demo` | `com.wbz` | Web (8080) |
| `activiti-demo` | `com.wbz.activiti` | Web (8083) |

## 快捷命令

```bash
# 编译全项目
mvn -q -DskipTests compile

# 运行指定模块
mvn -pl <module-name> spring-boot:run

# 仅编译指定模块及依赖
mvn -pl <module-name> -am compile
```

## 模块端口分配

| 端口 | 模块 |
|------|------|
| 8080 | spring-security-demo |
| 8081 | springai-demo |
| 8082 | springai-alibaba-demo |
| 8083 | activiti-demo |

## 编码规范

- 使用 Java 25 新特性（Records、文本块、模式匹配、虚拟线程等）
- controller / service / config 分层
- DTO 优先使用 `record` 而非 `class`
- 不写 Javadoc 注释，方法/类型命名自解释
- 不使用 Lombok

## 数据库

- PostgreSQL: `localhost:5432/postgres`
- 用户名: `postgres`
- 密码: `123456`
