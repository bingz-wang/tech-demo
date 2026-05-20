package com.wbz.demo.controller;

import com.wbz.demo.model.LoginUser;
import com.wbz.demo.utils.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 演示 SecurityUtils 如何跨方法级联读取当前用户信息。
 * <p>
 * 核心演示点：
 * 整个请求处理（Controller → Service 模拟）都在 同一个线程 上执行，
 * SecurityContextHolder 中的 Authentication 对象始终可用。
 * 无需在方法参数中传递 user，SecurityUtils 全局可读。
 * </p>
 */
@RestController
@RequestMapping("/api/user")
public class DemoController {

    /**
     * 获取当前登录用户的完整信息。
     * <p>
     * 这里故意调了三次 SecurityUtils，演示无需传参也能拿到数据。
     * </p>
     */
    @GetMapping("/me")
    public Map<String, Object> me() {
        Map<String, Object> result = new LinkedHashMap<>();

        // ---------- 场景 1：级联读取（无参数穿透） ----------
        // Controller 内不同方法之间的数据共享 — 全都从 ThreadLocal 读

        LoginUser user = SecurityUtils.getLoginUser();          // 读一次
        Long userId = SecurityUtils.getUserId();                // 再读一次
        String username = SecurityUtils.getUsername();          // 再读一次
        Long deptId = SecurityUtils.getDeptId();                // 再读一次

        result.put("currentUser", user);
        result.put("userId", userId);
        result.put("username", username);
        result.put("deptId", deptId);
        result.put("isAdmin", SecurityUtils.isAdmin(userId));

        // ---------- 场景 2：深层调用同样能读到 ----------
        // 模拟 Service 层调用 — 实际上还在同一线程
        result.put("fromSimulatedService", simulateServiceCall());

        // ---------- 场景 3：说明 ThreadLocal 作用范围 ----------
        result.put("_threadInfo", explainThreadLocal());

        return result;
    }

    // ──────────────────── 模拟 Service 层 ────────────────────

    /**
     * 模拟 Service 方法 — 不需要 Controller 传入 user 参数，
     * 直接通过 SecurityUtils 获取当前用户，因为还在同一个线程。
     */
    private Map<String, Object> simulateServiceCall() {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("serviceNote", "我从 Service 层也能读到当前登录用户，" +
                "因为和 Controller 在同一线程上");
        info.put("userIdFromService", SecurityUtils.getUserId());
        info.put("usernameFromService", SecurityUtils.getUsername());
        info.put("deptIdFromService", SecurityUtils.getDeptId());
        return info;
    }

    // ──────────────────── 线程信息说明 ────────────────────

    private Map<String, Object> explainThreadLocal() {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("currentThread", Thread.currentThread().getName());
        info.put("threadId", Thread.currentThread().threadId());
        info.put("原理说明",
                "SecurityContextHolder 底层是 ThreadLocal，" +
                "当前请求全程在【" + Thread.currentThread().getName() + "】线程上执行，" +
                "所以 Controller 和 Service 都能无参数传递地读到登录用户。");
        return info;
    }
}
