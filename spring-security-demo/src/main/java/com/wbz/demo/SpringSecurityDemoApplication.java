package com.wbz.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Security 演示项目入口。
 * <p>
 * 运行后：
 * <ol>
 *   <li>启动内嵌 Tomcat（端口 8080）</li>
 *   <li>Security Filter Chain 自动生效</li>
 *   <li>预置三个内存用户</li>
 * </ol>
 * </p>
 */
@SpringBootApplication
public class SpringSecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemoApplication.class, args);
    }
}
