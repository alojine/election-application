package com.President.Election.repository;

import com.President.Election.model.Candidate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository {
    List<Candidate> getAll();
}
