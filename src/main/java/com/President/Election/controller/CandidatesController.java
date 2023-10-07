package com.President.Election.controller;

import com.President.Election.DTO.CandidateDTO;
import com.President.Election.mapper.CandidateMapper;
import com.President.Election.repository.CandidateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/candidates")
public class CandidatesController {

    private final CandidateRepository candidateRepository;

    private final CandidateMapper candidateMapper;

    public CandidatesController(CandidateRepository candidateRepository, CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
    }

    @GetMapping
    public ResponseEntity<List<CandidateDTO>> getAll() {
        return new ResponseEntity<>(candidateMapper.candidatesToCandidateDTOs(candidateRepository.findAll()), HttpStatus.OK);
    }
}
