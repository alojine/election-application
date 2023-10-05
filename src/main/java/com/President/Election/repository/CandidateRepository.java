package com.President.Election.repository;

import com.President.Election.model.Candidate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository {
    List<Candidate> findAll();

    Optional<Candidate> findByVoteNumber(int voteNumber);
}
