package com.President.Election.service.Impl;

import com.President.Election.exception.NotFoundException;
import com.President.Election.model.Candidate;
import com.President.Election.repository.CandidateRepository;
import com.President.Election.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Candidate getByVoteNumber (int voteNumber) throws NotFoundException {
        return candidateRepository.findByVoteNumber(voteNumber)
                .orElseThrow(() -> new NotFoundException("Candidate was not found. Wrong wrong vote number provided"));
    }
}
