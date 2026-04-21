package com.test.prueba.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.test.prueba.mapper.VoteMapper;
import com.test.prueba.model.Candidate;
import com.test.prueba.model.Vote;
import com.test.prueba.model.Voter;
import com.test.prueba.model.dto.CastVoteRequest;
import com.test.prueba.model.dto.VoteResponse;
import com.test.prueba.repository.CandidateRepository;
import com.test.prueba.repository.VoteRepository;
import com.test.prueba.repository.VoterRepository;
import com.test.prueba.util.ApiException;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private VoterRepository voterRepository;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private VoteMapper voteMapper;

    @InjectMocks
    private VoteService voteService;

    private Voter voter;
    private Candidate candidate;

    @BeforeEach
    void setup() {
        voter = new Voter();
        voter.setId(1L);
        voter.setName("John Doe");
        voter.setEmail("john@example.com");
        voter.setHasVoted(false);

        candidate = new Candidate();
        candidate.setId(10L);
        candidate.setName("Jane Candidate");
        candidate.setParty("Party A");
        candidate.setVotes(0);
    }

    @Test
    void castVoteShouldSucceedAndUpdateState() {
        CastVoteRequest request = new CastVoteRequest(1L, 10L);
        Vote vote = new Vote();
        vote.setId(99L);
        vote.setCreatedAt(Instant.now());
        vote.setVoter(voter);
        vote.setCandidate(candidate);

        when(voterRepository.findById(1L)).thenReturn(Optional.of(voter));
        when(candidateRepository.findById(10L)).thenReturn(Optional.of(candidate));
        when(voteRepository.existsByVoterId(1L)).thenReturn(false);
        when(voteRepository.save(any(Vote.class))).thenReturn(vote);
        when(voteMapper.toResponse(vote)).thenReturn(new VoteResponse(99L, 1L, 10L, vote.getCreatedAt()));

        VoteResponse response = voteService.castVote(request);

        assertEquals(99L, response.id());
        assertTrue(voter.isHasVoted());
        assertEquals(1, candidate.getVotes());
        verify(voterRepository).save(voter);
        verify(candidateRepository).save(candidate);
    }

    @Test
    void castVoteShouldFailWhenVoterAlreadyVoted() {
        CastVoteRequest request = new CastVoteRequest(1L, 10L);
        voter.setHasVoted(true);

        when(voterRepository.findById(1L)).thenReturn(Optional.of(voter));
        when(candidateRepository.findById(10L)).thenReturn(Optional.of(candidate));

        ApiException ex = assertThrows(ApiException.class, () -> voteService.castVote(request));

        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertFalse(ex.getMessage().isBlank());
        verify(voteRepository, never()).save(any(Vote.class));
    }
}
