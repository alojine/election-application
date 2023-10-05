package com.President.Election.service.Impl;

import com.President.Election.DTO.CandidateDTO;
import com.President.Election.DTO.GeneralDistributionDTO;
import com.President.Election.model.Candidate;
import com.President.Election.model.Voter;
import com.President.Election.repository.VoteRepository;
import com.President.Election.service.CandidateService;
import com.President.Election.service.ResultService;
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

    private final VoteRepository voteRepository;

    @Autowired
    public ResultServiceImpl(CandidateService candidateService, VoteRepository voteRepository) {
        this.candidateService = candidateService;
        this.voteRepository = voteRepository;
    }

    public List<GeneralDistributionDTO> calculateGeneralVotingDistribution() {
        // GeneralDistributionDto does not contain a list with voter names, because data about voting is anonymous???
        List<GeneralDistributionDTO> generalDistribution = new ArrayList<>();

        List<Candidate> candidates = candidateService.getAll();
        List<Voter> voters = voteRepository.findAll();

        for (Candidate candidate : candidates){
            int numberOfVotes = 0;
            GeneralDistributionDTO generalDistributionDTO = new GeneralDistributionDTO();
            generalDistributionDTO.setCandidateDTO(
                    new CandidateDTO(
                            candidate.getFirstname(),
                            candidate.getLastname(),
                            candidate.getVoteNumber(),
                            candidate.getAgenda()
                    )
            );

            for(Voter voter : voters) {
                // increment number of votes if vote number matches
                if(candidate.getVoteNumber() == voter.getCandidate().getVoteNumber()) {
                    numberOfVotes++;
                }
            }

            generalDistributionDTO.setPercentageOfVotes(PercentageHelper.getPercentage(numberOfVotes, voters.size()));
            generalDistribution.add(generalDistributionDTO);
        }

        return generalDistribution;
    }

    @Override
    public List<CandidateDTO> getWinner() {
        List<CandidateDTO> winners = new ArrayList<>();

        List<GeneralDistributionDTO> generalDistributionDTOS = calculateGeneralVotingDistribution();

        // Sort the general distribution DTOs by percentage of votes in descending order
        generalDistributionDTOS.sort(Comparator.comparing(GeneralDistributionDTO::getPercentageOfVotes).reversed());

        GeneralDistributionDTO topCandidate = generalDistributionDTOS.get(0);
        // Check if top candidate has more than 50 percentage of voters
        if(topCandidate.getPercentageOfVotes().compareTo(BigDecimal.valueOf(50)) > 0) {
            winners.add(generalDistributionDTOS.get(0).getCandidateDTO());
            return winners;
        }
        // TODO: fix in case 3 are equal or 3 have no votes
        // Add two with the most percentage
        winners.add(topCandidate.getCandidateDTO());
        winners.add(generalDistributionDTOS.get(1).getCandidateDTO());
        return winners;
    }
}
