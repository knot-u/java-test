package com.test.prueba.repository;

import com.test.prueba.model.Voter;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepository extends JpaRepository<Voter, Long> {

    Optional<Voter> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByNameIgnoreCase(String name);

    long countByHasVotedTrue();
}
