package com.President.Election.service.Impl;

import com.President.Election.exception.NotFoundException;
import com.President.Election.model.Candidate;
import com.President.Election.repository.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class CandidateServiceImplTest {

    @InjectMocks
    private CandidateServiceImpl candidateService;

    @Mock
    private CandidateRepository candidateRepository;

    @Test
    public void whenGetAll_thenReturnAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        when(candidateRepository.findAll()).thenReturn(candidates);

        assertThat(candidateService.getAll()).isEqualTo(candidates);
    }

    @Test
    public void whenGetByVoteNumber_thenReturnCorrectCandidate() throws NotFoundException {
        when(candidateRepository.findByVoteNumber(anyInt())).thenReturn(Optional.of(provideCandidate()));

        assertThat(candidateService.getByVoteNumber(1)).isEqualTo(provideCandidate());
    }

    @Test
    public void whenGetByVoteNumber_withBadVoteNumber_thenThrowNotFoundException() {
        when(candidateRepository.findByVoteNumber(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> candidateService.getByVoteNumber(1));
    }

    private static Candidate provideCandidate() {
        return new Candidate(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                "Bill",
                "Gates",
                1,
                "Programmer, Investor, Founder of Microsoft"
        );
    }

}