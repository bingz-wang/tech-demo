package com.wbz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 普通用户接口 —— 需要 USER 或 ADMIN 角色。
 * <p>
 * URL 级别保护由 {@link com.wbz.config.SecurityConfig} 中的
 * {@code .requestMatchers("/api/user/**").hasRole("USER")} 实现。
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    /**
     * 获取当前登录用户的基本信息。
     * Authentication 对象由 Spring Security 自动注入。
     */
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> profile(Authentication auth) {
        return ResponseEntity.ok(Map.of(
                "username", auth.getName(),
                "roles", auth.getAuthorities().stream()
                        .map(Object::toString)
                        .toList(),
                "message", "认证成功！你已登录为用户角色。"
        ));
    }
}
