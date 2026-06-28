package com.mollanegash.regulator.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "regulatory_insight")
public class RegulatoryInsight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agency_name", nullable = false)
    private String agencyName;

    @Column(columnDefinition = "text")
    private String summary;

    @Column(name = "compliance_risk_level")
    private String complianceRiskLevel;

    @Column(name = "requirement_density")
    private double requirementDensity;

    @Column(name = "embedding_vector", columnDefinition = "text")
    @Convert(converter = FloatListConverter.class)
    private List<Float> embeddingVector;

    public RegulatoryInsight() {
    }

    public RegulatoryInsight(String agencyName, String summary, String complianceRiskLevel, List<Float> embeddingVector) {
        this.agencyName = agencyName;
        this.summary = summary;
        this.complianceRiskLevel = complianceRiskLevel;
        this.embeddingVector = embeddingVector;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getComplianceRiskLevel() {
        return complianceRiskLevel;
    }

    public void setComplianceRiskLevel(String complianceRiskLevel) {
        this.complianceRiskLevel = complianceRiskLevel;
    }

    public double getRequirementDensity() {
        return requirementDensity;
    }

    public void setRequirementDensity(double requirementDensity) {
        this.requirementDensity = requirementDensity;
    }

    public List<Float> getEmbeddingVector() {
        return embeddingVector;
    }

    public void setEmbeddingVector(List<Float> embeddingVector) {
        this.embeddingVector = embeddingVector;
    }
}
