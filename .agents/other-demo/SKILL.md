---
name: other-demo
description: 其他工具类模块 — Jasypt 加密、通用工具等。
---

# other-demo 开发指南

## 模块信息

- **包名**: `com.wbz`
- **类型**: 控制台程序

## 代码结构

```
other-demo/src/main/java/com/wbz/
├── Main.java                # 入口
└── jasypt/
    └── JasyptUtil.java      # Jasypt 加密/解密工具类
```

## 新增工具类规则

1. 按功能归类到子包（如 `jasypt/`、`io/`、`json/`）
2. 工具类方法使用 `static` 方法
3. 命名清晰表达功能，如 `XxxUtil`、`XxxHelper`
