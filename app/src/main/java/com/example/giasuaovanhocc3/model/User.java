package com.example.giasuaovanhocc3.model;

import java.util.Date;
import java.util.List;
public class User {

    private String id;
    private String fullName;
    private String email;
    private String passwordHash;
    private String className;
    private List<String> goals;
    private String avatarUrl;
    private Socials socials;
    private Date createdAt;
    private Date updatedAt;

    public static class Socials {
        private String googleId;
        private String facebookId;
        private String githubId;
        public Socials() {}
        public String getGoogleId() { return googleId; }
        public void setGoogleId(String googleId) { this.googleId = googleId; }
        public String getFacebookId() { return facebookId; }
        public void setFacebookId(String facebookId) { this.facebookId = facebookId; }
        public String getGithubId() { return githubId; }
        public void setGithubId(String githubId) { this.githubId = githubId; }
    }

    public User() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public List<String> getGoals() { return goals; }
    public void setGoals(List<String> goals) { this.goals = goals; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public Socials getSocials() { return socials; }
    public void setSocials(Socials socials) { this.socials = socials; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

}
