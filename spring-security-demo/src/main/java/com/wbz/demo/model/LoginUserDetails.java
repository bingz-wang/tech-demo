package com.wbz.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 适配层：将 {@link LoginUser} 包装为 Spring Security 的 {@link UserDetails}。
 * <p>
 * 当认证成功时，Spring Security 将本对象存入
 * {@code Authentication.getPrincipal()}。
 * {@link com.wbz.demo.utils.SecurityUtils} 通过
 * {@code principal instanceof LoginUserDetails} 判断并解包出 LoginUser。
 * </p>
 */
public class LoginUserDetails implements UserDetails {

    private final LoginUser loginUser;
    private final String password;

    public LoginUserDetails(LoginUser loginUser, String encodedPassword) {
        this.loginUser = loginUser;
        this.password = encodedPassword;
    }

    /**
     * 获取原始的业务用户对象。
     * SecurityUtils 通过此方法读取 userId / deptId 等字段。
     */
    public LoginUser getLoginUser() {
        return loginUser;
    }

    // ──── UserDetails 接口方法 ────

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return loginUser.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginUser.getUsername();
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

    @Override
    public String toString() {
        return "LoginUserDetails{" + loginUser + "}";
    }
}
