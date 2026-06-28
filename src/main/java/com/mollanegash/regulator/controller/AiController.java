package com.mollanegash.regulator.controller;

import com.mollanegash.regulator.service.RegulatoryIntelligenceService;
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

    @PostMapping(path = "/analyze", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> analyze(@RequestBody String query) {
        if (query == null || query.isBlank()) {
            return ResponseEntity.badRequest().body("Query must be a non-empty string.");
        }

        String result = intelligenceService.getRegulatoryInsight(query.trim());
        return ResponseEntity.ok(result);
    }
}
