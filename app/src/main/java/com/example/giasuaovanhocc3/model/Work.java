package com.example.giasuaovanhocc3.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.Map; // Import Map cho trường mindmap

public class Work {

    @BsonId
    private ObjectId id;

    @BsonProperty(value = "title")
    private String title;

    @BsonProperty(value = "authorId")
    private ObjectId authorId; // Tham chiếu đến _id của collection 'authors'

    @BsonProperty(value = "grade")
    private String grade;

    @BsonProperty(value = "summary")
    private String summary;

    @BsonProperty(value = "analysis")
    private String analysis;

    @BsonProperty(value = "mindmap")
    private Map<String, Object> mindmap; // Dùng Map<String, Object> để biểu diễn JSON object

    @BsonProperty(value = "tags")
    private List<String> tags;

    @BsonProperty(value = "createdAt")
    private Date createdAt;

    @BsonProperty(value = "updatedAt")
    private Date updatedAt;

    // Constructor rỗng cần thiết cho MongoDB POJO Codec
    public Work() {
    }

    // Constructor với các trường bắt buộc (title, authorId)
    public Work(String title, ObjectId authorId) {
        this.title = title;
        this.authorId = authorId;
    }

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ObjectId getAuthorId() {
        return authorId;
    }

    public void setAuthorId(ObjectId authorId) {
        this.authorId = authorId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public Map<String, Object> getMindmap() {
        return mindmap;
    }

    public void setMindmap(Map<String, Object> mindmap) {
        this.mindmap = mindmap;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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
        return "Work{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", grade='" + grade + '\'' +
                ", summary='" + summary + '\'' +
                ", analysis='" + analysis + '\'' +
                ", mindmap=" + mindmap +
                ", tags=" + tags +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
