package com.test.prueba.controller;

import com.test.prueba.model.dto.CastVoteRequest;
import com.test.prueba.model.dto.VoteResponse;
import com.test.prueba.model.dto.VoteStatisticsResponse;
import com.test.prueba.services.VoteService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoteResponse castVote(@Valid @RequestBody CastVoteRequest request) {
        return voteService.castVote(request);
    }

    @GetMapping
    public List<VoteResponse> getAllVotes() {
        return voteService.getAllVotes();
    }

    @GetMapping("/statistics")
    public VoteStatisticsResponse getStatistics() {
        return voteService.getStatistics();
    }
}
