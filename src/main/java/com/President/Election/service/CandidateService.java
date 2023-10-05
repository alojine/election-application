package com.President.Election.service;

import com.President.Election.exception.NotFoundException;
import com.President.Election.model.Candidate;

import java.util.List;

public interface CandidateService {
    List<Candidate> getAll();
    Candidate getByVoteNumber (int voteNumber) throws NotFoundException;
}
