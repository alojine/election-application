package com.President.Election.service;

import com.President.Election.model.Voter;

public interface VotingService {

    Voter registerVote (Voter voter) throws Exception;

    boolean validateVoter(Voter voter) throws Exception;
}
