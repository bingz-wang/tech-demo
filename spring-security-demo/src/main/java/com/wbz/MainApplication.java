package com.wbz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Security 学习 Demo - 启动入口
 *
 * 演示 Spring Security 在生产中的几个常用特性：
 * 1. 表单登录 + HTTP Basic 双模式认证
 * 2. 基于 URL 的 RBAC（角色访问控制）
 * 3. 方法级安全（@PreAuthorize）
 * 4. BCrypt 密码编码
 * 5. CSRF 保护策略
 * 6. 自定义 403 响应
 * 7. CORS 配置
 */
@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
