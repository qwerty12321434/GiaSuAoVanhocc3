package com.example.giasuaovanhocc3.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Work {

    private Integer id;
    private String title;
    private Integer authorId;
    private Integer grade;
    private String summary;
    private String analysis;
    private Map<String, Object> mindmap;
    private List<String> tags;
    private Date createdAt;
    private Date updatedAt;

    public Work() {
    }

    public Work(String title, Integer authorId) {
        this.title = title;
        this.authorId = authorId;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
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
