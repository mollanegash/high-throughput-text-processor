package com.mollanegash.regulator.controller;

import com.mollanegash.regulator.model.Agency;
import com.mollanegash.regulator.repository.AgencyRepository;
import com.mollanegash.regulator.service.EcfrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class WebController {
    private static final Logger log = LoggerFactory.getLogger(WebController.class);

    private final EcfrService ecfrService;
    private final AgencyRepository agencyRepository;

    // Manual Constructor (Replaces @RequiredArgsConstructor)
    public WebController(EcfrService ecfrService, AgencyRepository agencyRepository) {
        this.ecfrService = ecfrService;
        this.agencyRepository = agencyRepository;
    }

    @GetMapping("/")
    public String showDashboard(Model model) {
        List<Agency> agencies = agencyRepository.findAll();
        model.addAttribute("agencies", agencies);
        return "index";
    }

    @PostMapping("/sync")
    public String handleSync(@RequestParam(defaultValue = "20") int limit) {
        ecfrService.syncAgencies(limit);
        log.info("Sync complete with limit: {}", limit);
        return "redirect:/";
    }

    @GetMapping("/api/agencies")
    public ResponseEntity<List<Agency>> getRawData() {
        List<Agency> agencies = agencyRepository.findAll();
        return ResponseEntity.ok(agencies);
    }
}