package com.wbz.controller;

import com.wbz.model.AppUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 管理接口 —— 仅 ADMIN 角色可访问。
 * <p>
 * 演示两种授权方式：
 * 1. URL 级别保护：{@code .requestMatchers("/api/admin/**").hasRole("ADMIN")}
 * 2. 方法级别保护：{@link PreAuthorize} 注解（更精细的控制）
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    /**
     * 查看系统状态 —— 由 URL 级别的 hasRole("ADMIN") 保护
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status() {
        return ResponseEntity.ok(Map.of(
                "status", "running",
                "uptime", "演示模式",
                "message", "你拥有 ADMIN 角色，可以查看系统状态。"
        ));
    }

    /**
     * 查看所有用户 —— 由方法级的 @PreAuthorize 保护。
     * <p>
     * ★ 虽然 URL 规则已经是 /api/admin/**，但 @PreAuthorize 提供了
     *   第二层保险。如果 URL 规则将来发生变化，方法注解依然生效。
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> listUsers(Authentication auth) {
        return ResponseEntity.ok(Map.of(
                "adminUser", auth.getName(),
                "users", new String[]{"admin", "user"},
                "note", "只有 ADMIN 可以看到用户列表（@PreAuthorize 双重保护）"
        ));
    }

    /**
     * 模拟查看当前登录的管理员信息 —— 使用 Authentication 获取当前用户详情
     */
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(Authentication auth) {
        AppUser appUser = (AppUser) auth.getPrincipal();
        return ResponseEntity.ok(Map.of(
                "username", appUser.getUsername(),
                "roles", appUser.getRoles(),
                "message", "欢迎回来，管理员 " + appUser.getUsername() + "！"
        ));
    }
}
