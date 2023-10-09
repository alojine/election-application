package com.President.Election.controller;

import com.President.Election.DTO.CandidateDTO;
import com.President.Election.DTO.CandidatePercentageDTO;
import com.President.Election.DTO.RegionDistributionDTO;
import com.President.Election.exception.NotSelectableException;
import com.President.Election.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/results")
public class ResultsController {

    private final ResultService resultService;

    @Autowired
    public ResultsController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public ResponseEntity<List<CandidateDTO>> getWinner() throws NotSelectableException {
        return new ResponseEntity<>(resultService.getWinner(), HttpStatus.OK);
    }

    @GetMapping("/general")
    public ResponseEntity<List<CandidatePercentageDTO>> getGeneralVotingDistribution() {
        return new ResponseEntity<>(resultService.calculateGeneralVotingDistribution(), HttpStatus.OK);
    }

    @GetMapping("/regions")
    public ResponseEntity<List<RegionDistributionDTO>> getVotingDistributionByRegions() {
        return new ResponseEntity<>(resultService.calculateRegionVotingDistribution(), HttpStatus.OK);
    }
}
