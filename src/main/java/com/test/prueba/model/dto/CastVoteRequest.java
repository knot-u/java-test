package com.test.prueba.model.dto;

import jakarta.validation.constraints.NotNull;

public record CastVoteRequest(
        @NotNull(message = "voterId is required")
        Long voterId,
        @NotNull(message = "candidateId is required")
        Long candidateId
) {
}
