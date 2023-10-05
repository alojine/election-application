package com.President.Election.repository.Impl;

import com.President.Election.model.Candidate;
import com.President.Election.repository.CandidateRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CandidateRepositoryImpl implements CandidateRepository{
    private final List<Candidate> candidates = initializeCandidates();

    public List<Candidate> getAll() {
        return candidates;
    }

    private List<Candidate> initializeCandidates() {
        List<Candidate> primaryCandidates = new ArrayList<>();

        Candidate candidate = new Candidate();
        candidate.setId(1L);
        candidate.setFirstname("Tomas");
        candidate.setLastname("Tomaitis");
        candidate.setAgenda("+++");

        primaryCandidates.add(candidate);

        candidate.setId(2L);
        candidate.setFirstname("Jonas");
        candidate.setLastname("Jonaitis");
        candidate.setAgenda("---");

        primaryCandidates.add(candidate);

        candidate.setId(3L);
        candidate.setFirstname("Ponas");
        candidate.setLastname("Ponaitis");
        candidate.setAgenda("***");

        primaryCandidates.add(candidate);

        return primaryCandidates;
    }
}
