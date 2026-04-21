package com.test.prueba.services;

import com.test.prueba.mapper.VoteMapper;
import com.test.prueba.model.Candidate;
import com.test.prueba.model.Vote;
import com.test.prueba.model.Voter;
import com.test.prueba.model.dto.CandidateStatisticsResponse;
import com.test.prueba.model.dto.CastVoteRequest;
import com.test.prueba.model.dto.VoteResponse;
import com.test.prueba.model.dto.VoteStatisticsResponse;
import com.test.prueba.repository.CandidateRepository;
import com.test.prueba.repository.VoteRepository;
import com.test.prueba.repository.VoterRepository;
import com.test.prueba.util.ApiException;
import java.util.Comparator;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;
    private final VoteMapper voteMapper;

    public VoteService(
            VoteRepository voteRepository,
            VoterRepository voterRepository,
            CandidateRepository candidateRepository,
            VoteMapper voteMapper
    ) {
        this.voteRepository = voteRepository;
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
        this.voteMapper = voteMapper;
    }

    @Transactional
    public VoteResponse castVote(CastVoteRequest request) {
        Voter voter = voterRepository.findById(request.voterId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Voter not found"));

        Candidate candidate = candidateRepository.findById(request.candidateId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Candidate not found"));

        if (voter.isHasVoted() || voteRepository.existsByVoterId(voter.getId())) {
            throw new ApiException(HttpStatus.CONFLICT, "Voter already cast a vote");
        }

        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setCandidate(candidate);

        Vote savedVote = voteRepository.save(vote);

        voter.setHasVoted(true);
        candidate.setVotes(candidate.getVotes() + 1);

        voterRepository.save(voter);
        candidateRepository.save(candidate);

        return voteMapper.toResponse(savedVote);
    }

    @Transactional(readOnly = true)
    public List<VoteResponse> getAllVotes() {
        return voteRepository.findAll().stream().map(voteMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public VoteStatisticsResponse getStatistics() {
        long totalVotes = voteRepository.count();
        long totalVotersWhoVoted = voterRepository.countByHasVotedTrue();

        List<CandidateStatisticsResponse> perCandidate = candidateRepository.findAll().stream()
                .sorted(Comparator.comparingLong(Candidate::getVotes).reversed())
                .map(candidate -> {
                    double percentage = totalVotes == 0
                            ? 0.0
                            : ((double) candidate.getVotes() / (double) totalVotes) * 100.0;
                    return new CandidateStatisticsResponse(
                            candidate.getId(),
                            candidate.getName(),
                            candidate.getVotes(),
                            roundTwoDecimals(percentage)
                    );
                })
                .toList();

        return new VoteStatisticsResponse(totalVotes, totalVotersWhoVoted, perCandidate);
    }

    private double roundTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
