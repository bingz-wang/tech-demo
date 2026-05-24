---
name: project-workflows
description: 项目级通用工作流 — 新增模块、编译验证、运行调试、代码审查等日常开发流程。
---

# 项目工作流

## 新增学习模块

严格执行以下步骤：

```
1. 在根 pom.xml 的 <modules> 中添加 <module>xxx-demo</module>
2. 创建模块目录: xxx-demo/src/main/java/com/wbz/xxx/{config,controller,service}/
3. 编写 pom.xml（以 spring-security-demo 为模板，复制 BOM、spring-boot-maven-plugin 结构）
4. 创建 Application 主类 + application.yml
5. 端口递增分配（当前已用: 8080~8083，下一个: 8084）
6. 更新 agents.md、README.md 和 .agents/ 下的对应 skill 文件
7. 运行 mvn -q -DskipTests compile 验证通过
```

## 编译验证

```bash
# 全量编译
mvn -q -DskipTests compile

# 单模块编译（含依赖模块）
mvn -pl <module> -am -q -DskipTests compile
```

## 运行模块

```bash
# Spring Boot 模块
mvn -pl <module> spring-boot:run

# 控制台模块 — IDE 中直接运行 Main 类
```

## 代码规范检查清单

提交前确认：
- [ ] DTO 使用 `record` 而非 `class`
- [ ] controller / service / config 三层分离
- [ ] 无冗余注释，命名自解释
- [ ] 无 Lombok，不引入非标准注解处理器
- [ ] 无未使用的 import
- [ ] `mvn -q -DskipTests compile` 通过
