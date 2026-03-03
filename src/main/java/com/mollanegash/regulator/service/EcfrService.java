package com.mollanegash.regulator.service;

import com.mollanegash.regulator.model.AgencyDto;
import com.mollanegash.regulator.model.AgencyResponse;
import com.mollanegash.regulator.model.Agency;
import com.mollanegash.regulator.repository.AgencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EcfrService {

    // 1. Manual Logger (Replaces @Slf4j)
    private static final Logger log = LoggerFactory.getLogger(EcfrService.class);

    private final AgencyRepository repository;
    private final RestClient restClient = RestClient.create();
    private final ObjectMapper mapper = new ObjectMapper();

    // 2. Manual Constructor (Replaces @RequiredArgsConstructor)
    public EcfrService(AgencyRepository repository) {
        this.repository = repository;
    }

    public void syncAgencies(int limit) {
        log.info("Starting sync for {} agencies...", limit);

        AgencyResponse response = restClient.get()
                .uri("https://www.ecfr.gov/api/admin/v1/agencies.json")
                .retrieve()
                .body(AgencyResponse.class);

        if (response != null && response.agencies() != null) {
            repository.deleteAll();

            response.agencies().parallelStream()
                    .limit(limit)
                    .forEach(this::processAgency);

            repository.flush();
            log.info("Successfully synced and persisted {} agencies.", limit);
        }
    }

    private void processAgency(AgencyDto dto) {
        try {
            String rawJson = fetchAgencySearchJson(dto.slug());
            String text = extractRegulatoryText(rawJson);

            if (text.isEmpty()) {
                text = "Regulatory data for " + dto.name() + " is currently being indexed.";
            }

            // Standard Java initialization instead of .builder()
            Agency agency = new Agency();
            agency.setSlug(dto.slug());
            agency.setName(dto.name());
            agency.setWordCount(countWords(text));
            agency.setChecksum(generateChecksum(text));
            agency.setRequirementDensity(calculateDensity(text));
            agency.setLastUpdated(java.time.LocalDateTime.now().toString());

            repository.save(agency);
            log.debug("Processed agency: {}", dto.slug());
        } catch (Exception e) {
            log.error("Failed to process agency {}: {}", dto.slug(), e.getMessage());
        }
    }

    private String fetchAgencySearchJson(String slug) {
        try {
            String url = "https://www.ecfr.gov/api/search/v1/results?" +
                    "agency_slug=" + slug +
                    "&per_page=50" +
                    "&order=newest";

            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            log.warn("Search API failed for {}. Returning empty results.", slug);
            return "{\"results\": []}";
        }
    }

    private String extractRegulatoryText(String jsonResponse) {
        try {
            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode results = root.path("results");
            StringBuilder sb = new StringBuilder();

            for (JsonNode node : results) {
                sb.append(node.path("description").asText("")).append(" ");
                sb.append(node.path("headline").asText("")).append(" ");
                if (node.has("full_text")) {
                    sb.append(node.path("full_text").asText("")).append(" ");
                }
            }
            return sb.toString().trim();
        } catch (Exception e) {
            log.error("JSON Parsing error: {}", e.getMessage());
            return "";
        }
    }

    private long countWords(String text) {
        if (text == null || text.isBlank()) return 0;
        return text.trim().split("\\s+").length;
    }

    private String generateChecksum(String text) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(text.getBytes());
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            return "n/a";
        }
    }

    private double calculateDensity(String text) {
        if (text == null || text.isBlank()) return 0.0;

        Pattern pattern = Pattern.compile("(?i)\\b(shall|must|required|prohibited|may not|authority|compliance|enforce|penalty)\\b");
        Matcher matcher = pattern.matcher(text);

        long hitCount = 0;
        while (matcher.find()) {
            hitCount++;
        }

        long totalWords = countWords(text);
        return totalWords == 0 ? 0 : (double) hitCount / totalWords;
    }
}