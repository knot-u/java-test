package com.test.prueba.services;

import com.test.prueba.mapper.CandidateMapper;
import com.test.prueba.model.Candidate;
import com.test.prueba.model.dto.CandidateResponse;
import com.test.prueba.model.dto.CreateCandidateRequest;
import com.test.prueba.repository.CandidateRepository;
import com.test.prueba.repository.VoterRepository;
import com.test.prueba.util.ApiException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final VoterRepository voterRepository;
    private final CandidateMapper candidateMapper;

    public CandidateService(
            CandidateRepository candidateRepository,
            VoterRepository voterRepository,
            CandidateMapper candidateMapper
    ) {
        this.candidateRepository = candidateRepository;
        this.voterRepository = voterRepository;
        this.candidateMapper = candidateMapper;
    }

    @Transactional
    public CandidateResponse create(CreateCandidateRequest request) {
        if (candidateRepository.existsByNameIgnoreCase(request.name())) {
            throw new ApiException(HttpStatus.CONFLICT, "Candidate name already exists");
        }

        if (voterRepository.existsByNameIgnoreCase(request.name())) {
            throw new ApiException(HttpStatus.CONFLICT, "A voter cannot be registered as candidate");
        }

        Candidate candidate = new Candidate();
        candidate.setName(request.name().trim());
        candidate.setParty(request.party() == null ? null : request.party().trim());
        candidate.setVotes(0);

        return candidateMapper.toResponse(candidateRepository.save(candidate));
    }

    @Transactional(readOnly = true)
    public List<CandidateResponse> getAll() {
        return candidateRepository.findAll().stream()
                .sorted((a, b) -> Long.compare(b.getVotes(), a.getVotes()))
                .map(candidateMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public Candidate findEntityById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Candidate not found"));
    }

    @Transactional(readOnly = true)
    public CandidateResponse getById(Long id) {
        return candidateMapper.toResponse(findEntityById(id));
    }

    @Transactional
    public void deleteById(Long id) {
        Candidate candidate = findEntityById(id);
        if (candidate.getVotes() > 0) {
            throw new ApiException(HttpStatus.CONFLICT, "Cannot delete a candidate with registered votes");
        }
        candidateRepository.delete(candidate);
    }
}
