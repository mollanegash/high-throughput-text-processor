package com.mollanegash.regulator.controller;

import jakarta.validation.constraints.NotBlank;

public record AnalyzeRequest(@NotBlank(message = "Query must not be blank") String query) {
}
