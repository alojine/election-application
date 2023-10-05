package com.President.Election.service;

import com.President.Election.DTO.CandidateDTO;
import com.President.Election.DTO.GeneralDistributionDTO;

import java.util.List;

public interface ResultService {
    List<GeneralDistributionDTO> calculateGeneralVotingDistribution();

    List<CandidateDTO> getWinner();
}
