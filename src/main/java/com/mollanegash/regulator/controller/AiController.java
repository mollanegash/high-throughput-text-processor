package com.mollanegash.regulator.controller;

import com.mollanegash.regulator.model.RegulatoryAnalysis;
import com.mollanegash.regulator.service.RegulatoryIntelligenceService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final RegulatoryIntelligenceService intelligenceService;

    public AiController(RegulatoryIntelligenceService intelligenceService) {
        this.intelligenceService = intelligenceService;
    }

    @PostMapping(
            path = "/analyze",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AnalyzeResponse> analyze(@Valid @RequestBody AnalyzeRequest request) {

        String result = intelligenceService.getRegulatoryInsight(request.query().trim());

        return ResponseEntity.ok(new AnalyzeResponse(result, null));
    }

    @PostMapping(
            path = "/analyze-agency",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RegulatoryAnalysis> analyzeAgency(@Valid @RequestBody AnalyzeRequest request) {

        return ResponseEntity.ok(
                intelligenceService.performAnalysis(request.query().trim())
        );
    }
}

