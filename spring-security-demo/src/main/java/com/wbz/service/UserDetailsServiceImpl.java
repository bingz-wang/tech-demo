package com.wbz.service;

import com.wbz.model.AppUser;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户加载服务 —— 实现 Spring Security 的 UserDetailsService。
 * <p>
 * 生产环境中通常从数据库查询用户，此处用内存 Map 模拟，
 * 在 {@link #initUsers()} 中预置了演示用户。
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final Map<String, AppUser> userStore = new ConcurrentHashMap<>();

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 预置两个演示用户（密码均为 12345，BCrypt 编码存储）
     */
    @PostConstruct
    public void initUsers() {
        saveUser("admin", "12345", Set.of("ADMIN", "USER"));
        saveUser("user", "12345", Set.of("USER"));
    }

    private void saveUser(String username, String rawPassword, Set<String> roles) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        userStore.put(username, new AppUser(username, encodedPassword, roles));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userStore.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }
        return user;
    }
}
