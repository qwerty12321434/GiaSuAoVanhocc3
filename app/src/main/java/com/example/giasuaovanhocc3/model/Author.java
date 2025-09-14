package com.example.giasuaovanhocc3.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Date;
public class Author {

    @BsonId
    private ObjectId id;

    @BsonProperty(value = "name")
    private String name;

    @BsonProperty(value = "bio")
    private String bio;

    @BsonProperty(value = "birthYear")
    private Integer birthYear; // Sử dụng Integer để cho phép giá trị null

    @BsonProperty(value = "deathYear")
    private Integer deathYear; // Sử dụng Integer để cho phép giá trị null

    @BsonProperty(value = "createdAt")
    private Date createdAt;

    @BsonProperty(value = "updatedAt")
    private Date updatedAt;

    // Constructor rỗng cần thiết cho MongoDB POJO Codec
    public Author() {
    }

    // Constructor với các trường bắt buộc (ví dụ: name)
    public Author(String name) {
        this.name = name;
    }

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
