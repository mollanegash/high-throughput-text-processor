package com.mollanegash.regulator.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "agency")
public class Agency {
    @Id
    @Column(nullable = false, updatable = false)
    private String slug;

    @Column(nullable = false)
    private String name;

    @Column(name = "word_count", nullable = false)
    private long wordCount;

    @Column(nullable = false)
    private String checksum;

    @Column(name = "requirement_density", nullable = false)
    private double requirementDensity;

    @Column(name = "last_updated")
    private String lastUpdated;

    public Agency() {
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWordCount() {
        return wordCount;
    }

    public void setWordCount(long wordCount) {
        this.wordCount = wordCount;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public double getRequirementDensity() {
        return requirementDensity;
    }

    public void setRequirementDensity(double requirementDensity) {
        this.requirementDensity = requirementDensity;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}