package com.President.Election.service;

import com.President.Election.DTO.CandidateDTO;
import com.President.Election.DTO.CandidatePercentageDTO;
import com.President.Election.DTO.RegionDistributionDTO;
import com.President.Election.exception.NotSelectableException;

import java.util.List;

public interface ResultService {
    List<CandidatePercentageDTO> calculateGeneralVotingDistribution();

    List<CandidateDTO> getWinner() throws NotSelectableException;

    List<RegionDistributionDTO> calculateRegionVotingDistribution();
}
