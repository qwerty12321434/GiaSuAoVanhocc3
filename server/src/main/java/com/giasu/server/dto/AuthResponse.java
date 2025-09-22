package com.giasu.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.giasu.server.model.User;

public class AuthResponse {
    private String token;
    private UserResponse user;

    public AuthResponse() {}

    public AuthResponse(String token, UserResponse user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public UserResponse getUser() { return user; }
    public void setUser(UserResponse user) { this.user = user; }

    public static class UserResponse {
        private Long id;
        private String name;
        private String email;
        
        @JsonProperty("class")
        private String className;
        
        private String role;
        private String createdAt;

        public UserResponse() {}

        public UserResponse(User user) {
            this.id = user.getId();
            this.name = user.getFullName();
            this.email = user.getEmail();
            this.className = user.getClassName();
            this.role = user.getRole();
            this.createdAt = user.getCreatedAt() != null ? user.getCreatedAt().toString() : null;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    }
}