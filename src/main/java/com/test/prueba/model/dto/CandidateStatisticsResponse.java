package com.test.prueba.model.dto;

public record CandidateStatisticsResponse(
        Long candidateId,
        String candidateName,
        long totalVotes,
        double percentage
) {
}
