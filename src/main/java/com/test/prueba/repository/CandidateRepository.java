package com.test.prueba.repository;

import com.test.prueba.model.Candidate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    boolean existsByNameIgnoreCase(String name);

    Optional<Candidate> findByNameIgnoreCase(String name);
}
