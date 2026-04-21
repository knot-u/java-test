package com.test.prueba.controller;

import com.test.prueba.model.dto.CandidateResponse;
import com.test.prueba.model.dto.CreateCandidateRequest;
import com.test.prueba.services.CandidateService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CandidateResponse create(@Valid @RequestBody CreateCandidateRequest request) {
        return candidateService.create(request);
    }

    @GetMapping
    public List<CandidateResponse> getAll() {
        return candidateService.getAll();
    }

    @GetMapping("/{id}")
    public CandidateResponse getById(@PathVariable Long id) {
        return candidateService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        candidateService.deleteById(id);
    }
}
