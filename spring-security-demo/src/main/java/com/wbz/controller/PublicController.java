package com.wbz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 公开接口 —— 无需登录即可访问，用于验证安全框架是否正常生效。
 */
@RestController
@RequestMapping("/api/public")
public class PublicController {

    @GetMapping("/hello")
    public ResponseEntity<Map<String, Object>> hello() {
        return ResponseEntity.ok(Map.of(
                "message", "你好，这是一个公开接口，无需认证！",
                "note", "如果看到这段 JSON，说明应用已成功启动，Security 正在运行。"
        ));
    }

    @GetMapping("/time")
    public ResponseEntity<Map<String, Object>> serverTime() {
        return ResponseEntity.ok(Map.of(
                "serverTime", System.currentTimeMillis(),
                "timezone", java.time.ZoneId.systemDefault().toString()
        ));
    }
}
