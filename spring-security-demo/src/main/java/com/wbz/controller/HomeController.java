package com.wbz.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 根路径控制器 —— 返回当前用户状态和可用的测试接口说明。
 * <p>
 * 该路径在 SecurityConfig 中配置为 permitAll，但会显示不同的内容
 * 取决于用户是否已登录。
 */
@RestController
public class HomeController {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> home(Authentication auth, HttpServletRequest request) {
        boolean isLoggedIn = auth != null && auth.isAuthenticated()
                && !"anonymousUser".equals(auth.getPrincipal());

        Map<String, Object> body = Map.of(
                "project", "Spring Security 学习 Demo",
                "version", "1.0",
                "loginStatus", isLoggedIn ? "已登录" : "未登录",
                "currentUser", isLoggedIn ? auth.getName() : null,
                "tips", """
                        可用测试端点（推荐使用 curl 或 Postman 测试）：
                        ─────────────────────────────────────────────
                        🔓 公开  GET  /api/public/hello     → 无需认证
                        🔓 公开  GET  /api/public/time      → 无需认证
                        🔒 用户  GET  /api/user/profile     → USER/ADMIN
                        🔒 管理员 GET  /api/admin/status    → ADMIN
                        🔒 管理员 GET  /api/admin/users     → ADMIN (@PreAuthorize)
                        🔒 管理员 GET  /api/admin/me        → ADMIN

                        🧪 测试命令：
                        curl -u user:12345  http://localhost:8080/api/user/profile
                        curl -u admin:12345 http://localhost:8080/api/admin/users
                        """,
                "testCommands", Map.of(
                        "public", "curl http://localhost:8080/api/public/hello",
                        "asUser", "curl -u user:12345 http://localhost:8080/api/user/profile",
                        "asAdmin", "curl -u admin:12345 http://localhost:8080/api/admin/status",
                        "adminOnly", "curl -u admin:12345 http://localhost:8080/api/admin/users",
                        "forbiddenAsUser", "curl -u user:12345 http://localhost:8080/api/admin/status"
                )
        );
        return ResponseEntity.ok(body);
    }
}
