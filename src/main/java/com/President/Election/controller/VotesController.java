package com.President.Election.controller;

import com.President.Election.DTO.VoteDTO;
import com.President.Election.DTO.VoterDTO;
import com.President.Election.enums.Region;
import com.President.Election.mapper.VoterMapper;
import com.President.Election.model.Voter;
import com.President.Election.service.CandidateService;
import com.President.Election.service.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/votes")
public class VotesController {

    private final CandidateService candidateService;
    private final VoterService voterService;
    private final VoterMapper voterMapper;

    @Autowired
    public VotesController(CandidateService candidateService, VoterService voterService, VoterMapper voterMapper) {
        this.candidateService = candidateService;
        this.voterService = voterService;
        this.voterMapper = voterMapper;
    }

    @PostMapping
    public ResponseEntity<VoterDTO> vote (
            @RequestBody @Validated VoteDTO voteDto
    ) throws Exception {
        Voter voter = new Voter();
        voter.setId(UUID.randomUUID());
        voter.setName(voteDto.name());
        voter.setRegion(Region.isValidRegion(voteDto.region()));
        voter.setCandidate(candidateService.getByVoteNumber(voteDto.candidateNumber()));
        return new ResponseEntity<>(voterMapper.voterToVoterDTO(voterService.registerVote(voter)), HttpStatus.OK);
    }
}
