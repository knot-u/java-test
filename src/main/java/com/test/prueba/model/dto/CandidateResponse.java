package com.test.prueba.model.dto;

public record CandidateResponse(
        Long id,
        String name,
        String party,
        long votes
) {
}
