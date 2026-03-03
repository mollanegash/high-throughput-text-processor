package com.mollanegash.regulator.model;

import jakarta.persistence.*;

@Entity
public class Agency {
    @Id
    private String slug;
    private String name;
    private long wordCount;
    private String checksum;
    private double requirementDensity;
    private String lastUpdated;

    public Agency() {} // Required by JPA

    // Standard Getters and Setters
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public long getWordCount() { return wordCount; }
    public void setWordCount(long wordCount) { this.wordCount = wordCount; }
    public String getChecksum() { return checksum; }
    public void setChecksum(String checksum) { this.checksum = checksum; }
    public double getRequirementDensity() { return requirementDensity; }
    public void setRequirementDensity(double requirementDensity) { this.requirementDensity = requirementDensity; }
    public String getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }
}