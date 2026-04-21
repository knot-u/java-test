package com.test.prueba.model.dto;

import java.util.List;

public record VoteStatisticsResponse(
        long totalVotes,
        long totalVotersWhoVoted,
        List<CandidateStatisticsResponse> results
) {
}
