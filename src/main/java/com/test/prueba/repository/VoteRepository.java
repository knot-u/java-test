package com.test.prueba.repository;

import com.test.prueba.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    boolean existsByVoterId(Long voterId);
}
