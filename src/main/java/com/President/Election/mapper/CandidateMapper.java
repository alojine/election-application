package com.President.Election.mapper;

import com.President.Election.DTO.CandidateDTO;
import com.President.Election.model.Candidate;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CandidateMapper {
    CandidateDTO candidateToCandidateDTO(Candidate candidate);

    List<CandidateDTO> candidatesToCandidateDTOs(List<Candidate> candidates);
}
