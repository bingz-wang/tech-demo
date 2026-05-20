package com.wbz.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 应用用户实体 —— 实现 Spring Security 的 UserDetails 接口。
 * <p>
 * 生产环境中通常对应数据库中的 user 表，此处简化为内存模型。
 */
public class AppUser implements UserDetails {

    private final String username;
    private final String password;
    private final Set<String> roles;

    public AppUser(String username, String password, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    // ──────────────────────────────────────────────
    // UserDetails 接口实现
    // ──────────────────────────────────────────────

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 将角色名（如 "ADMIN"）转为 GrantedAuthority，加 "ROLE_" 前缀
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // ──────────────────────────────────────────────
    // 业务方法
    // ──────────────────────────────────────────────

    public Set<String> getRoles() {
        return roles;
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    @Override
    public String toString() {
        return "AppUser{username='" + username + "', roles=" + roles + "}";
    }
}
