package com.President.Election.service.Impl;

import com.President.Election.enums.Region;
import com.President.Election.exception.HasVotedException;
import com.President.Election.model.Voter;
import com.President.Election.repository.VoterRepository;
import com.President.Election.service.VoterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VoterServiceImpl implements VoterService {

    private final VoterRepository voterRepository;

    @Autowired
    public VoterServiceImpl(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    @Override
    public List<Voter> getAll() {
        return voterRepository.findAll();
    }

    @Override
    public List<Voter> getAllByRegion(Region region) {
        List<Voter> votersFromRegion = new ArrayList<>();
        for (Voter voter : voterRepository.findAll()) {
            if(voter.getRegion() == region) {
                votersFromRegion.add(voter);
            }
        }
        return votersFromRegion;
    }

    @Override
    public Voter registerVote(Voter voter) throws Exception {
        log.info("Voter validation has started");
        if (voterRepository.findByName(voter.getName()) != null) {
            log.debug("Voter was not validated");
            Voter voterFromDb = voterRepository.findByName(voter.getName());
            if(voterFromDb.getCandidate().equals(voter.getCandidate())) {
                throw new HasVotedException("Voter with id:" + voter.getId() + "has already voted. Voter cannot vote for same candidate twice.");
            }
            throw new HasVotedException("Voter with id:" + voter.getId() + "has already voted. Voter cannot vote for two different candidates.");
        }
        log.info("Voter validation has finished");
        return voterRepository.save(voter);
    }
}
