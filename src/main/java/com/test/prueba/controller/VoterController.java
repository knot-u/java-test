package com.test.prueba.controller;

import com.test.prueba.model.dto.CreateVoterRequest;
import com.test.prueba.model.dto.VoterResponse;
import com.test.prueba.services.VoterService;
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
@RequestMapping("/voters")
public class VoterController {

    private final VoterService voterService;

    public VoterController(VoterService voterService) {
        this.voterService = voterService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoterResponse create(@Valid @RequestBody CreateVoterRequest request) {
        return voterService.create(request);
    }

    @GetMapping
    public List<VoterResponse> getAll() {
        return voterService.getAll();
    }

    @GetMapping("/{id}")
    public VoterResponse getById(@PathVariable Long id) {
        return voterService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        voterService.deleteById(id);
    }
}
