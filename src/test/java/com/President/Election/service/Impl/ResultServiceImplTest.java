package com.President.Election.service.Impl;

import com.President.Election.DTO.CandidateDTO;
import com.President.Election.DTO.CandidatePercentageDTO;
import com.President.Election.DTO.RegionDistributionDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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

    @Test
    public void whenCalculateGeneralVotingDistribution_thenReturnCorrectVotersAndPercentages() {
        List<Voter> oneVoterForBill = provideVoterList();
        List<Candidate> Bill = provideCandidateList();

        when(candidateService.getAll()).thenReturn(Bill);
        when(voterService.getAll()).thenReturn(oneVoterForBill);


        assertThat(resultService.calculateGeneralVotingDistribution()).isEqualTo(provideCandidatePercentageList());
    }

    @Test
    public void whenPickWinner_thenPrickSingleWinner() {
        List<Voter> oneVoterForBill = provideVoterList();
        List<Candidate> Bill = provideCandidateList();

        when(candidateService.getAll()).thenReturn(Bill);
        when(voterService.getAll()).thenReturn(oneVoterForBill);


        assertThat(resultService.getWinner()).isEqualTo(provideCandidateDTOList());
    }

    @Test
    public void whenPickWinner_thenPickTwoWinners() {
        List<Voter> voteForBillAndVoteForJon = provideTwoVoterList();
        List<Candidate> BillAndJon = provideTwoCandidateList();

        when(candidateService.getAll()).thenReturn(BillAndJon);
        when(voterService.getAll()).thenReturn(voteForBillAndVoteForJon);


        assertThat(resultService.getWinner()).isEqualTo(provideTwoCandidateDTOList());
    }

    @Test
    public void whenCalculateRegionDistribution_thenPrickSingleWinner() {
        List<Voter> oneVoterForBill = provideVoterList();
        List<Candidate> Bill = provideCandidateList();

        when(candidateService.getAll()).thenReturn(Bill);
        when(voterService.getAllByRegion(Region.VILNIUS)).thenReturn(oneVoterForBill);

        assertThat(resultService.calculateRegionVotingDistribution()).isEqualTo(provideRegionDistribution());
    }


    private static List<RegionDistributionDTO> provideRegionDistribution() {
        List<RegionDistributionDTO> regionDistributionDTOList = new ArrayList<>();

        for (Region region : Region.values()){
            RegionDistributionDTO regionDistributionDTO = new RegionDistributionDTO();
            // there is only one voter in vilnius, therefore 100% ov voters are in VILNIUS for Bill
            if(region == Region.VILNIUS) {
                regionDistributionDTO.setRegion(Region.VILNIUS);
                regionDistributionDTO.setCandidatePercentageDTO(provideCandidatePercentageList());
            } else {
                regionDistributionDTO.setRegion(region);
                regionDistributionDTO.setCandidatePercentageDTO(provideCandidatePercentageListWithZeroPercentage());
            }
            regionDistributionDTOList.add(regionDistributionDTO);
        }

        return regionDistributionDTOList;
    }

    private static List<CandidateDTO> provideCandidateDTOList() {
        List<CandidateDTO> candidateDTOS = new ArrayList<>();
        candidateDTOS.add(
                new CandidateDTO(
                        "Bill",
                        "Gates",
                        1,
                        "Programmer, Investor, Founder of Microsoft"
                )
        );
        return candidateDTOS;
    }

    private static List<CandidateDTO> provideTwoCandidateDTOList() {
        List<CandidateDTO> candidateDTOS = new ArrayList<>();
        candidateDTOS.add(
                new CandidateDTO(
                        "Bill",
                        "Gates",
                        1,
                        "Programmer, Investor, Founder of Microsoft"
                )
        );
        candidateDTOS.add(
                new CandidateDTO(
                        "Jon",
                        "Jones",
                        2,
                        "Mixed martial arts and UFC fighter."
                )
        );
        return candidateDTOS;
    }

    private static List<Voter> provideTwoVoterList() {
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
        voters.add(
                new Voter(
                        UUID.fromString("e58ed763-928c-4155-bee9-fdbaaadc15f3"),
                        "Domas",
                        Region.VILNIUS,
                        new Candidate(
                                UUID.fromString("e58ed763-928c-4155-bee9-fdbaaadc15f3"),
                                "Jon",
                                "Jones",
                                2,
                                "Mixed martial arts and UFC fighter."
                        )
                )
        );
        return voters;
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

    private static List<Candidate> provideTwoCandidateList() {
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
        candidates.add(
                new Candidate(
                        UUID.fromString("e58ed763-928c-4155-bee9-fdbaaadc15f3"),
                        "Jon",
                        "Jones",
                        2,
                        "Mixed martial arts and UFC fighter."
                )
        );
        return candidates;
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

    private static List<CandidatePercentageDTO> provideCandidatePercentageListWithZeroPercentage() {
        List<CandidatePercentageDTO> candidatePercentageDTOS = new ArrayList<>();
        candidatePercentageDTOS.add(
                new CandidatePercentageDTO(
                        new CandidateDTO(
                                "Bill",
                                "Gates",
                                1,
                                "Programmer, Investor, Founder of Microsoft"),
                        BigDecimal.ZERO
                )
        );
        return candidatePercentageDTOS;
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