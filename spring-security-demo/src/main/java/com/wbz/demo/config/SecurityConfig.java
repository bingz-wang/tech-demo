package com.wbz.demo.config;

import com.wbz.demo.model.LoginUser;
import com.wbz.demo.model.LoginUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

/**
 * Spring Security 核心配置。
 * <p>
 * 关键机制说明（配合 SecurityUtils 的理解）：
 * <ol>
 *   <li><b>SecurityContextHolderFilter</b> — 在每个请求开始时从 Session 恢复
 *       SecurityContext 并绑定到 {@code SecurityContextHolder}（ThreadLocal）；
 *       请求结束时自动 <b>清除</b>，防止线程复用读到脏数据。</li>
 *   <li><b>SecurityContextHolder</b> — 底层是一个 {@link ThreadLocal}，
 *       因此 Controller/Service 层不需要参数传递就能拿到当前用户，
 *       前提是和 Filter 链在 <b>同一个线程</b> 上执行。</li>
 *   <li>本配置使用内存用户（纯 demo，无数据库依赖）。</li>
 * </ol>
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 内存用户详情服务 —— 预置三个用户。
     * 返回 {@link LoginUserDetails}，其 {@code getPrincipal()} 包含 {@link LoginUser}。
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        return username -> {
            LoginUser loginUser;
            String rawPassword;

            switch (username) {
                case "admin":
                    loginUser = new LoginUser(1L, 1L, "admin", "系统管理员",
                            List.of("admin", "user"));
                    rawPassword = "admin123";
                    break;
                case "zhangsan":
                    loginUser = new LoginUser(2L, 2L, "zhangsan", "张三",
                            List.of("user"));
                    rawPassword = "123456";
                    break;
                case "lisi":
                    loginUser = new LoginUser(3L, 3L, "lisi", "李四",
                            List.of("user"));
                    rawPassword = "123456";
                    break;
                default:
                    throw new UsernameNotFoundException("用户不存在: " + username);
            }

            return new LoginUserDetails(loginUser, encoder.encode(rawPassword));
        };
    }

    /**
     * Security Filter Chain 配置。
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 所有请求需认证
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                // 表单登录（POST /api/login）
                .formLogin(form -> form
                        .loginProcessingUrl("/api/login")
                        .successHandler((request, response, authentication) -> {
                            response.setContentType("application/json;charset=utf-8");
                            String username = SecurityUtilsShim.getCurrentUsername();
                            response.getWriter().write(
                                    "{\"code\":200,\"msg\":\"登录成功\",\"user\":\""
                                            + username + "\"}");
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setContentType("application/json;charset=utf-8");
                            response.setStatus(401);
                            response.getWriter().write(
                                    "{\"code\":401,\"msg\":\"用户名或密码错误\"}");
                        })
                )
                // 注销（POST /api/logout）
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setContentType("application/json;charset=utf-8");
                            response.getWriter().write(
                                    "{\"code\":200,\"msg\":\"注销成功\"}");
                        })
                )
                // 禁用 CSRF（REST API 不需要）
                .csrf(csrf -> csrf.disable())
                ;
        return http.build();
    }

    // ──────────────────────────────────────────
    // 辅助内部类：避免 SecurityConfig 直接依赖 SecurityUtils
    // ──────────────────────────────────────────
    private static class SecurityUtilsShim {
        static String getCurrentUsername() {
            var auth = org.springframework.security.core.context
                    .SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal()
                    instanceof LoginUserDetails lud) {
                return lud.getLoginUser().getDisplayName();
            }
            return auth != null ? auth.getName() : "unknown";
        }
    }
}
