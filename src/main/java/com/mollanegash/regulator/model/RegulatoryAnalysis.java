package com.mollanegash.regulator.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "regulatory_analysis")
public class RegulatoryAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_query", nullable = false, columnDefinition = "text")
    private String userQuery;

    @Column(name = "ai_response", columnDefinition = "text")
    private String aiResponse;

    @Column(name = "requirement_density")
    private double requirementDensity;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regulatory_insight_id")
    private RegulatoryInsight relatedInsight;

    public RegulatoryAnalysis() {
    }

    public RegulatoryAnalysis(String userQuery, String aiResponse, double requirementDensity, RegulatoryInsight relatedInsight, LocalDateTime createdAt) {
        this.userQuery = userQuery;
        this.aiResponse = aiResponse;
        this.requirementDensity = requirementDensity;
        this.relatedInsight = relatedInsight;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getUserQuery() {
        return userQuery;
    }

    public void setUserQuery(String userQuery) {
        this.userQuery = userQuery;
    }

    public String getAiResponse() {
        return aiResponse;
    }

    public void setAiResponse(String aiResponse) {
        this.aiResponse = aiResponse;
    }

    public double getRequirementDensity() {
        return requirementDensity;
    }

    public void setRequirementDensity(double requirementDensity) {
        this.requirementDensity = requirementDensity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public RegulatoryInsight getRelatedInsight() {
        return relatedInsight;
    }

    public void setRelatedInsight(RegulatoryInsight relatedInsight) {
        this.relatedInsight = relatedInsight;
    }
}
