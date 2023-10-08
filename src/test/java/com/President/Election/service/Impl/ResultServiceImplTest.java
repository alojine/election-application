package com.President.Election.service.Impl;

import com.President.Election.DTO.CandidateDTO;
import com.President.Election.DTO.CandidatePercentageDTO;
import com.President.Election.enums.Region;
import com.President.Election.model.Candidate;
import com.President.Election.model.Voter;
import com.President.Election.service.CandidateService;
import com.President.Election.service.VoterService;
import com.President.Election.utility.PercentageHelper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ResultServiceImplTest {

    @InjectMocks
    private ResultServiceImpl resultService;

    @Mock
    private CandidateService candidateService;

    @Mock
    private VoterService voterService;

    @Test
    public void whenCalculatePercentageForCandidates_thenReturnCorrectPercentages() {
        List<Voter> oneVoterForBill = provideVoterList();
        List<Candidate> Bill = provideCandidateList();

        assertThat(resultService.calculatePercentageForCandidates(Bill, oneVoterForBill)).isEqualTo(provideCandidatePercentageList());
    }

    private static List<Voter> provideVoterList() {
        List<Voter> voters = new ArrayList<>();
        voters.add(
                new Voter(
                        UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                        "Tomas",
                        Region.VILNIUS,
                        new Candidate(
                                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                                "Bill",
                                "Gates",
                                1,
                                "Programmer, Investor, Founder of Microsoft"
                        )
                )
        );
        return voters;
    }

    private static List<Candidate> provideCandidateList() {
        List<Candidate> candidates = new ArrayList<>();
        candidates.add(
                new Candidate(
                        UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                        "Bill",
                        "Gates",
                        1,
                        "Programmer, Investor, Founder of Microsoft"
                )
        );
        return candidates;
    }

    private static List<CandidatePercentageDTO> provideCandidatePercentageList() {
        List<CandidatePercentageDTO> candidatePercentageDTOS = new ArrayList<>();
        BigDecimal percentage = BigDecimal.valueOf(100.01).setScale(1, RoundingMode.FLOOR);
        candidatePercentageDTOS.add(
                new CandidatePercentageDTO(
                        new CandidateDTO(
                                "Bill",
                                "Gates",
                                1,
                                "Programmer, Investor, Founder of Microsoft"),
                        PercentageHelper.getPercentage(1, 1)
                )
        );
        return candidatePercentageDTOS;
    }

}