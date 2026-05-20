package com.wbz.demo.utils;

import com.wbz.demo.model.LoginUser;
import com.wbz.demo.model.LoginUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring Security 上下文工具类。
 * <p>
 * 核心机制：{@link SecurityContextHolder} 底层使用 {@link ThreadLocal}，
 * 将当前线程的认证信息与线程绑定。
 * 一次 HTTP 请求的整个生命周期（Filter → Controller → Service → Response）
 * 都在同一个线程中执行，因此这里的方法都能读到同一个 Authentication 对象。
 * 请求结束后，SecurityContextHolderFilter 会自动清空 ThreadLocal。
 * </p>
 *
 * <pre>
 * ┌─ HTTP Request ─────────────────────────────────────┐
 * │  Tomcat 线程池分配线程 T1                           │
 * │  ① Security Filter 链 → 创建 Authentication        │
 * │  ② 写入 ThreadLocal (SecurityContextHolder)        │
 * │  ③ Controller/Service  → 读取 ThreadLocal (此处)  │
 * │  ④ clearContext() 自动清理                         │
 * └────────────────────────────────────────────────────┘
 * </pre>
 */
public final class SecurityUtils {

    private SecurityUtils() {}

    // ──────────────────────────────────────────────
    // 密码编码（BCrypt）
    // ──────────────────────────────────────────────

    public static String encryptPassword(String rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }

    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }

    // ──────────────────────────────────────────────
    // 当前用户信息（核心 — 基于 ThreadLocal）
    // ──────────────────────────────────────────────

    /**
     * 获取当前线程绑定的 {@link Authentication} 对象。
     * 这是整个工具类的数据源头。
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前登录用户（完整 LoginUser 对象）。
     * 在匿名 / 未认证时返回 null。
     */
    public static LoginUser getLoginUser() {
        Authentication auth = getAuthentication();
        if (auth == null) return null;
        Object principal = auth.getPrincipal();
        if (principal instanceof LoginUserDetails) {
            return ((LoginUserDetails) principal).getLoginUser();
        }
        return null;
    }

    public static Long getUserId() {
        LoginUser user = getLoginUser();
        return user != null ? user.getUserId() : null;
    }

    public static String getUsername() {
        LoginUser user = getLoginUser();
        return user != null ? user.getUsername() : null;
    }

    public static Long getDeptId() {
        LoginUser user = getLoginUser();
        return user != null ? user.getDeptId() : null;
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    /**
     * 当前用户是否已认证（已登录）
     */
    public static boolean isAuthenticated() {
        Authentication auth = getAuthentication();
        return auth != null && auth.isAuthenticated()
                && !"anonymousUser".equals(auth.getPrincipal());
    }
}
