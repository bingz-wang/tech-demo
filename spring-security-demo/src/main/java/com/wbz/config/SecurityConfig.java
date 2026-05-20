package com.wbz.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.nio.charset.StandardCharsets;

/**
 * Spring Security 核心配置。
 *
 * 演示特性：
 * - 表单登录 + HTTP Basic 双模式认证
 * - 基于 URL 的 RBAC
 * - 方法级安全（@EnableMethodSecurity）
 * - CSRF 策略
 * - CORS 配置
 * - 自定义 403 处理
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity   // 开启 @PreAuthorize / @PostAuthorize 方法级安全
public class SecurityConfig {

    /**
     * 密码编码器：BCrypt（生产中最常用的密码哈希算法）
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 安全过滤链 —— 定义所有 URL 的访问规则。
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // ── 授权规则 ─────────────────────────────────────
            .authorizeHttpRequests(auth -> auth
                // 公开端点（无需认证）
                .requestMatchers("/", "/index.html", "/api/public/**").permitAll()
                // 普通用户端点（USER 或 ADMIN 均可访问）
                .requestMatchers("/api/user/**").hasRole("USER")
                // 管理端点（仅 ADMIN）
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                // 其余所有请求需认证
                .anyRequest().authenticated()
            )

            // ── 认证方式 ─────────────────────────────────────
            // 表单登录（使用 Spring Security 默认登录页）
            .formLogin(form -> form
                .loginPage("/login")       // 自定义登录页（可后续扩展）
                .permitAll()
            )
            // HTTP Basic（方便 curl / Postman 测试 REST API）
            .httpBasic(httpBasic -> {})

            // ── CSRF 保护 ────────────────────────────────────
            // 本 demo 为 REST 风格，暂时禁用 CSRF。
            // ★ 生产注意事项：传统服务端渲染的 Web 应用（Thymeleaf / JSP）中，
            //   CSRF 保护必须启用，可参考 configureCsrf() 中的注释。
            .csrf(csrf -> csrf.disable())

            // ── CORS ─────────────────────────────────────────
            // 允许前端跨域请求（前后端分离场景）
            .cors(cors -> {})

            // ── 异常处理 ─────────────────────────────────────
            .exceptionHandling(ex -> ex
                .accessDeniedHandler(accessDeniedHandler())
            );

        return http.build();
    }

    // ────────────────────────────────────────────────────────────
    // 如果要启用 CSRF（生产推荐），注释掉上面的 .csrf(csrf->csrf.disable())
    // 并取消注释下面这段，它会将 CSRF token 通过 cookie 暴露给前端：
    //
    // .csrf(csrf -> csrf
    //     .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
    // )
    // ────────────────────────────────────────────────────────────

    /**
     * 自定义 403 拒绝访问处理器 —— 返回 JSON 而非重定向到错误页。
     * 适合 REST API 风格。
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            String json = """
                    {
                        "code": 403,
                        "message": "权限不足，需要更高级别的角色才能访问此资源"
                    }
                    """;
            response.getWriter().write(json);
        };
    }
}
