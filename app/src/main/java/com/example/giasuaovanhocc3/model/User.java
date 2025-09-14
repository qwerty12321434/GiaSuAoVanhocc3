package com.example.giasuaovanhocc3.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
public class User {

    @BsonId // Đánh dấu đây là trường _id trong MongoDB
    private ObjectId id;

    @BsonProperty(value = "fullName") // Ánh xạ tới trường 'fullName'
    private String fullName;

    @BsonProperty(value = "email")
    private String email;

    @BsonProperty(value = "passwordHash") // Thêm trường mật khẩu đã mã hóa
    private String passwordHash;

    @BsonProperty(value = "class")
    private String className; // Đổi tên để tránh trùng với từ khóa 'class' trong Java

    @BsonProperty(value = "goals")
    private List<String> goals;

    @BsonProperty(value = "avatarUrl")
    private String avatarUrl;

    @BsonProperty(value = "socials")
    private Socials socials;

    @BsonProperty(value = "createdAt")
    private Date createdAt;

    @BsonProperty(value = "updatedAt")
    private Date updatedAt;

    // Lớp tĩnh lồng nhau để đại diện cho đối tượng 'socials'
    public static class Socials {
        @BsonProperty(value = "googleId")
        private String googleId;

        @BsonProperty(value = "facebookId")
        private String facebookId;

        @BsonProperty(value = "githubId")
        private String githubId;

        // Constructor, Getters và Setters cho Socials
        public Socials() {}

        public String getGoogleId() { return googleId; }
        public void setGoogleId(String googleId) { this.googleId = googleId; }
        public String getFacebookId() { return facebookId; }
        public void setFacebookId(String facebookId) { this.facebookId = facebookId; }
        public String getGithubId() { return githubId; }
        public void setGithubId(String githubId) { this.githubId = githubId; }
    }

    // Constructor rỗng là bắt buộc để driver có thể giải mã BSON thành đối tượng Java
    public User() {}

    // Getters và Setters cho tất cả các trường của User
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
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
