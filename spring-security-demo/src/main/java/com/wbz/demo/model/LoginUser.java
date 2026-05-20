package com.wbz.demo.model;

import java.util.List;

/**
 * 当前登录用户的信息载体（纯 POJO）。
 */
public class LoginUser {

    private Long userId;
    private Long deptId;
    private String username;
    private String displayName;
    private List<String> roles;

    public LoginUser() {}

    public LoginUser(Long userId, Long deptId, String username, String displayName, List<String> roles) {
        this.userId = userId;
        this.deptId = deptId;
        this.username = username;
        this.displayName = displayName;
        this.roles = roles;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getDeptId() { return deptId; }
    public void setDeptId(Long deptId) { this.deptId = deptId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    @Override
    public String toString() {
        return "LoginUser{userId=" + userId + ", deptId=" + deptId
                + ", username='" + username + "', displayName='" + displayName
                + "', roles=" + roles + "}";
    }
}
