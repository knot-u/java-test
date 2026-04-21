package com.test.prueba.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.prueba.model.dto.CandidateStatisticsResponse;
import com.test.prueba.model.dto.VoteResponse;
import com.test.prueba.model.dto.VoteStatisticsResponse;
import com.test.prueba.security.JwtAuthenticationFilter;
import com.test.prueba.services.VoteService;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(VoteController.class)
class VoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VoteService voteService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void shouldCastVote() throws Exception {
        VoteResponse response = new VoteResponse(1L, 100L, 200L, Instant.parse("2026-01-01T00:00:00Z"));
        when(voteService.castVote(any())).thenReturn(response);

        String body = "{\"voterId\":100,\"candidateId\":200}";

        mockMvc.perform(post("/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.voterId").value(100))
                .andExpect(jsonPath("$.candidateId").value(200));
    }

    @Test
    void shouldReturnStatistics() throws Exception {
        VoteStatisticsResponse stats = new VoteStatisticsResponse(
                5,
                5,
                List.of(new CandidateStatisticsResponse(200L, "Candidate A", 5, 100.0))
        );
        when(voteService.getStatistics()).thenReturn(stats);

        mockMvc.perform(get("/votes/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalVotes").value(5))
                .andExpect(jsonPath("$.totalVotersWhoVoted").value(5))
                .andExpect(jsonPath("$.results[0].candidateId").value(200))
                .andExpect(jsonPath("$.results[0].percentage").value(100.0));
    }
}
