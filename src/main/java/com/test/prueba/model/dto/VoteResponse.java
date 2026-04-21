package com.test.prueba.model.dto;

import java.time.Instant;

public record VoteResponse(
        Long id,
        Long voterId,
        Long candidateId,
        Instant createdAt
) {
}
