package com.President.Election.service.Impl;

import com.President.Election.exception.HasVotedException;
import com.President.Election.model.Voter;
import com.President.Election.repository.VoteRepository;
import com.President.Election.service.VotingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VotingServiceImpl implements VotingService {

    private final VoteRepository voteRepository;

    @Autowired
    public VotingServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public Voter registerVote(Voter voter) throws Exception {
        log.info("Voter validation has started");
        if (voteRepository.findByNameAndRegion(voter.getName(), voter.getRegion()) != null) {
            Voter voterFromDb = voteRepository.findByNameAndRegion(voter.getName(), voter.getRegion());
            if(voterFromDb.getCandidate() == voterFromDb.getCandidate()) {
                throw new HasVotedException("Voter with id:" + voter.getId() + "has already voted. Voter cannot vote for same candidate twice");
            } else if (voterFromDb.getCandidate() != voterFromDb.getCandidate()) {
                throw new HasVotedException("Voter with id:" + voter.getId() + "has already voted. Voter cannot vote for two different candidates");
            }
        }
        log.info("Voter validation has finished");
        return voteRepository.save(voter);
    }
}
