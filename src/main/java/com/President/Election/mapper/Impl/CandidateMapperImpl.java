package com.President.Election.mapper.Impl;

import com.President.Election.DTO.CandidateDTO;
import com.President.Election.mapper.CandidateMapper;
import com.President.Election.model.Candidate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CandidateMapperImpl implements CandidateMapper {

    @Override
    public CandidateDTO candidateToCandidateDTO(Candidate candidate) {
        if (candidate == null) {
            return null;
        }
        return new CandidateDTO(
                candidate.getFirstname(),
                candidate.getLastname(),
                candidate.getVoteNumber(),
                candidate.getAgenda()
        );
    }

    @Override
    public List<CandidateDTO> candidatesToCandidateDTOs(List<Candidate> candidates) {
        if (candidates == null) {
            return null;
        }
        return candidates.stream()
                .map(this::candidateToCandidateDTO)
                .collect(Collectors.toList());
    }
}
