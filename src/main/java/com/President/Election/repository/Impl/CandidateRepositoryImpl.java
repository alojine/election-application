package com.President.Election.repository.Impl;

import com.President.Election.model.Candidate;
import com.President.Election.repository.CandidateRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CandidateRepositoryImpl implements CandidateRepository{
    private final List<Candidate> candidates = initializeCandidates();

    @Override
    public List<Candidate> findAll() {
        return candidates;
    }

    @Override
    public Candidate findByVoteNumber(int voteNumber) throws Exception {
        for(Candidate candidate : candidates) {
            if (candidate.getVoteNumber() == voteNumber)
                return candidate;
        }
        throw new Exception("No candidate found. Candidate vote number is invalid.");
    }

    private List<Candidate> initializeCandidates() {
        List<Candidate> primaryCandidates = new ArrayList<>();

        Candidate candidateBill = new Candidate(
                UUID.randomUUID(),
                "Bill",
                "Gates",
                1,
                "Programmer, Investor, Founder of Microsoft");

        Candidate candidateJon = new Candidate(
                UUID.randomUUID(),
                "Jon",
                "Jones",
                2,
                "Mixed martial arts and UFC fighter.");

        Candidate candidateBenjamin = new Candidate(
                UUID.randomUUID(),
                "Benjamin",
                "Franklin",
                3,
                "Politician, writer, inventor, scientist");

        primaryCandidates.add(candidateBill);
        primaryCandidates.add(candidateJon);
        primaryCandidates.add(candidateBenjamin);

        return primaryCandidates;
    }
}