package com.mollanegash.regulator.model;

import java.time.Instant;

public record ApiErrorResponse(String error, String message, Instant timestamp) {
}
