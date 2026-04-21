package com.test.prueba.services;

import com.test.prueba.mapper.VoterMapper;
import com.test.prueba.model.Voter;
import com.test.prueba.model.dto.CreateVoterRequest;
import com.test.prueba.model.dto.VoterResponse;
import com.test.prueba.repository.CandidateRepository;
import com.test.prueba.repository.VoterRepository;
import com.test.prueba.util.ApiException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoterService {

    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;
    private final VoterMapper voterMapper;

    public VoterService(VoterRepository voterRepository, CandidateRepository candidateRepository, VoterMapper voterMapper) {
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
        this.voterMapper = voterMapper;
    }

    @Transactional
    public VoterResponse create(CreateVoterRequest request) {
        if (voterRepository.existsByEmailIgnoreCase(request.email())) {
            throw new ApiException(HttpStatus.CONFLICT, "Voter email already exists");
        }

        if (candidateRepository.existsByNameIgnoreCase(request.name())) {
            throw new ApiException(HttpStatus.CONFLICT, "A candidate cannot be registered as voter");
        }

        Voter voter = new Voter();
        voter.setName(request.name().trim());
        voter.setEmail(request.email().trim().toLowerCase());
        voter.setHasVoted(false);

        return voterMapper.toResponse(voterRepository.save(voter));
    }

    @Transactional(readOnly = true)
    public List<VoterResponse> getAll() {
        return voterRepository.findAll().stream().map(voterMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Voter findEntityById(Long id) {
        return voterRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Voter not found"));
    }

    @Transactional(readOnly = true)
    public VoterResponse getById(Long id) {
        return voterMapper.toResponse(findEntityById(id));
    }

    @Transactional
    public void deleteById(Long id) {
        Voter voter = findEntityById(id);
        if (voter.isHasVoted()) {
            throw new ApiException(HttpStatus.CONFLICT, "Cannot delete a voter who already voted");
        }
        voterRepository.delete(voter);
    }
}
