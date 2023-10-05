package com.President.Election.controller;

import com.President.Election.DTO.CandidateDTO;
import com.President.Election.DTO.GeneralDistributionDTO;
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

    @GetMapping
    public ResponseEntity<List<CandidateDTO>> getWinner() {
        return new ResponseEntity<>(resultService.getWinner(), HttpStatus.OK);
    }

    @Autowired
    public ResultsController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/general")
    public ResponseEntity<List<GeneralDistributionDTO>> getGeneralVotingDistribution() {
        return new ResponseEntity<>(resultService.calculateGeneralVotingDistribution(), HttpStatus.OK);
    }

    @GetMapping("/regions")
    public ResponseEntity<List<?>> getVotingDistributionByRegions() {
        return null;
    }
}
