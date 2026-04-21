package com.test.prueba.model.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCandidateRequest(
        @NotBlank(message = "Name is required")
        String name,
        String party
) {
}
