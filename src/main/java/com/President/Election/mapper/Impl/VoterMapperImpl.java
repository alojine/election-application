package com.President.Election.mapper.Impl;

import com.President.Election.DTO.VoterDTO;
import com.President.Election.mapper.CandidateMapper;
import com.President.Election.mapper.VoterMapper;
import com.President.Election.model.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoterMapperImpl implements VoterMapper {

    private final CandidateMapper candidateMapper;

    @Autowired
    public VoterMapperImpl(CandidateMapper candidateMapper) {
        this.candidateMapper = candidateMapper;
    }

    @Override
    public VoterDTO voterToVoterDTO(Voter voter) {
        if(voter == null) {
            return null;
        }
        return new VoterDTO(
                voter.getName(),
                voter.getRegion(),
                candidateMapper.candidateToCandidateDTO(voter.getCandidate())
        );
    }
}
