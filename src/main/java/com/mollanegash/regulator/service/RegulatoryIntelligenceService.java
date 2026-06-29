package com.mollanegash.regulator.service;

import com.mollanegash.regulator.model.RegulatoryAnalysis;
import com.mollanegash.regulator.model.RegulatoryInsight;

public interface RegulatoryIntelligenceService {
    String getRegulatoryInsight(String query);
    RegulatoryAnalysis performAnalysis(String agencyName);
}