package com.test.prueba.model.dto;

public record VoterResponse(
        Long id,
        String name,
        String email,
        boolean hasVoted
) {
}
