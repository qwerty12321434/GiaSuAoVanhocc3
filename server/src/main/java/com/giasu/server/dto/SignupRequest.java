package com.giasu.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignupRequest {
    @JsonProperty("full_name")
    public String fullName;
    
    // Also accept "name" for backward compatibility with Android app
    @JsonProperty("name")
    public String name;
    
    public String email;
    public String password;
    
    @JsonProperty("class")
    public String className;

    public SignupRequest() {}

    public SignupRequest(String fullName, String email, String password, String className) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.className = className;
    }

    // Getter that returns either fullName or name (whichever is set)
    public String getFullName() { 
        return fullName != null ? fullName : name; 
    }
    
    public void setFullName(String fullName) { 
        this.fullName = fullName; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
}