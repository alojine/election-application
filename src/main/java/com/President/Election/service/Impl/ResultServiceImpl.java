package com.President.Election.service.Impl;

import com.President.Election.DTO.CandidateDTO;
import com.President.Election.DTO.CandidatePercentageDTO;
import com.President.Election.DTO.RegionDistributionDTO;
import com.President.Election.enums.Region;
import com.President.Election.exception.NotSelectableException;
import com.President.Election.model.Candidate;
import com.President.Election.model.Voter;
import com.President.Election.service.CandidateService;
import com.President.Election.service.ResultService;
import com.President.Election.service.VoterService;
import com.President.Election.utility.PercentageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class ResultServiceImpl implements ResultService {

    private final CandidateService candidateService;

    private final VoterService voterService;

    @Autowired
    public ResultServiceImpl(CandidateService candidateService, VoterService voterService) {
        this.candidateService = candidateService;
        this.voterService = voterService;
    }

    @Override
    public List<CandidatePercentageDTO> calculateGeneralVotingDistribution() {
        List<Candidate> candidates = candidateService.getAll();
        List<Voter> voters = voterService.getAll();

        // GeneralDistributionDto does not contain a list with voters, because data about voting is anonymous???
        return calculatePercentageForCandidates(candidates, voters);
    }

    @Override
    public List<CandidateDTO> getWinner() throws NotSelectableException {
        List<CandidateDTO> winners = new ArrayList<>();
        List<Candidate> candidates = candidateService.getAll();
        List<Voter> voters = voterService.getAll();

        List<CandidatePercentageDTO> candidatePercentageDTOS = calculatePercentageForCandidates(candidates, voters);

        // Sort the candidate percentage DTOs by percentage of votes in descending order
        candidatePercentageDTOS.sort(Comparator.comparing(CandidatePercentageDTO::getPercentageOfVotes).reversed());

        CandidatePercentageDTO topCandidate = candidatePercentageDTOS.get(0);
        // Check if top candidate has more than 50 percentage of voters
        if(topCandidate.getPercentageOfVotes().compareTo(BigDecimal.valueOf(50)) > 0) {
            winners.add(candidatePercentageDTOS.get(0).getCandidateDTO());
            log.debug("Single winner has been selected.");
            return winners;
        }
        // Check if Three top candidates have the same amount of votes
        if ( topCandidate.getPercentageOfVotes().equals(candidatePercentageDTOS.get(1).getPercentageOfVotes()) &&
                candidatePercentageDTOS.get(1).getPercentageOfVotes().equals(candidatePercentageDTOS.get(2).getPercentageOfVotes())
        ) {
            throw new NotSelectableException("Winner is not selectable. Top three candidates have the same percentage of votes.");
            // Second and third place candidates have the same amount of votes
        } else if (topCandidate.getPercentageOfVotes().compareTo(candidatePercentageDTOS.get(1).getPercentageOfVotes()) > 0 &&
                candidatePercentageDTOS.get(1).getPercentageOfVotes().equals(candidatePercentageDTOS.get(2).getPercentageOfVotes())) {
            throw new NotSelectableException("Winner is not selectable. Second and third place candidates have the same amount of votes");
        }

        // Add two with the most percentage
        log.info("Two winners have been selected");
        winners.add(topCandidate.getCandidateDTO());
        winners.add(candidatePercentageDTOS.get(1).getCandidateDTO());
        return winners;
    }

    @Override
    public List<RegionDistributionDTO> calculateRegionVotingDistribution() {
        List<RegionDistributionDTO> regionDistribution = new ArrayList<>();
        List<Candidate> candidates = candidateService.getAll();

        // Looping through regions
        for (Region region : Region.values()) {
            RegionDistributionDTO regionDistributionDTO = new RegionDistributionDTO();
            regionDistributionDTO.setRegion(region);

            // Get voters from current region
            List<Voter> votersByRegion = voterService.getAllByRegion(region);
            List<CandidatePercentageDTO> candidatePercentageDTOS = calculatePercentageForCandidates(candidates, votersByRegion);

            regionDistributionDTO.setCandidatePercentageDTO(candidatePercentageDTOS);
            regionDistribution.add(regionDistributionDTO);
        }

        return regionDistribution;
    }

    public List<CandidatePercentageDTO>  calculatePercentageForCandidates(List<Candidate> candidates, List<Voter> voters) {
        List<CandidatePercentageDTO> generalDistribution = new ArrayList<>();

        for (Candidate candidate : candidates){
            int numberOfVotes = 0;
            CandidatePercentageDTO candidatePercentageDTO = new CandidatePercentageDTO();
            candidatePercentageDTO.setCandidateDTO(
                    new CandidateDTO(
                            candidate.getFirstname(),
                            candidate.getLastname(),
                            candidate.getVoteNumber(),
                            candidate.getAgenda()
                    )
            );

            for(Voter voter : voters) {
                // Increment number of votes if vote number matches
                if(candidate.getVoteNumber() == voter.getCandidate().getVoteNumber()) {
                    numberOfVotes++;
                }
            }

            // Use PercentageHelper to get percentage
            candidatePercentageDTO.setPercentageOfVotes(PercentageHelper.getPercentage(numberOfVotes, voters.size()));
            generalDistribution.add(candidatePercentageDTO);
        }

        return generalDistribution;
    }
}
