package com.President.Election.service;

import com.President.Election.exception.NotFoundException;
import com.President.Election.model.Candidate;

public interface CandidateService {
    Candidate getByVoteNumber (int voteNumber) throws NotFoundException;
}
