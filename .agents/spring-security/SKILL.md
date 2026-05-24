---
name: spring-security-demo
description: Spring Security 认证与授权学习模块 — Filter Chain、UserDetails、角色权限。
---

# spring-security-demo 开发指南

## 模块信息

- **包名**: `com.wbz`, `com.wbz.demo`, `com.wbz.config`, `com.wbz.controller`, `com.wbz.model`, `com.wbz.service`
- **类型**: Spring Boot Web（端口 **8080**）
- **启动**: `mvn -pl spring-security-demo spring-boot:run`

## 代码结构

```
spring-security-demo/src/main/java/com/wbz/
├── MainApplication.java                  # 入口1
├── demo/
│   └── SpringSecurityDemoApplication.java # 入口2
├── config/
│   └── SecurityConfig.java                # SecurityFilterChain 配置
├── controller/
│   ├── HomeController.java
│   ├── PublicController.java
│   ├── UserController.java
│   └── AdminController.java
├── model/
│   ├── AppUser.java
│   ├── LoginUser.java
│   └── LoginUserDetails.java
├── service/
│   └── UserDetailsServiceImpl.java
└── util/
    └── SecurityUtils.java
```

## 核心概念

| 概念 | 对应文件 |
|------|----------|
| SecurityFilterChain | `config/SecurityConfig.java` |
| UserDetailsService | `service/UserDetailsServiceImpl.java` |
| 认证流程 | 用户名/密码 → SecurityContext |
| 角色授权 | `ROLE_USER` / `ROLE_ADMIN` |
