package com.President.Election.controller;

import com.President.Election.DTO.CandidateDTO;
import com.President.Election.DTO.CandidatePercentageDTO;
import com.President.Election.DTO.RegionDistributionDTO;
import com.President.Election.enums.Region;
import com.President.Election.exception.NotSelectableException;
import com.President.Election.service.ResultService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ResultsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ResultService resultService;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void whenGetWinner_withThreeEqualCandidates_throwNotSelectableException() throws Exception {
        when(resultService.getWinner()).thenThrow(new NotSelectableException("Not selectable"));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/results")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void whenGetWinner_returnCorrectDTO() throws Exception {
        when(resultService.getWinner()).thenReturn(provideCandidateDTOList());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/results")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(provideCandidateDTOList())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void whenGetGeneralVotingDistribution_thenProvideCorrectDTOS() throws Exception {
        when(resultService.calculateGeneralVotingDistribution()).thenReturn(provideCandidatePercentageDTOList());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/results/general")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(provideCandidatePercentageDTOList())))
                .andReturn();
    }

    @Test
    public void whenGetVotingDistributionByRegions_thenProvideCorrectDTOS() throws Exception {
        when(resultService.calculateRegionVotingDistribution()).thenReturn(provideRegionDistribution());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/results/regions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(provideRegionDistribution())))
                .andReturn();
    }

    private static List<RegionDistributionDTO> provideRegionDistribution() {
        List<RegionDistributionDTO> regionDistributionDTOList = new ArrayList<>();

        for (Region region : Region.values()){
            RegionDistributionDTO regionDistributionDTO = new RegionDistributionDTO();
            regionDistributionDTO.setRegion(region);
            regionDistributionDTO.setCandidatePercentageDTO(provideCandidatePercentageListWithZeroPercentage());
            regionDistributionDTOList.add(regionDistributionDTO);
        }

        return regionDistributionDTOList;
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
        candidatePercentageDTOS.add(
                new CandidatePercentageDTO(
                        new CandidateDTO(
                                "Jon",
                                "Jones",
                                2,
                                "Mixed martial arts and UFC fighter."),
                        BigDecimal.ZERO
                )
        );
        candidatePercentageDTOS.add(
                new CandidatePercentageDTO(
                        new CandidateDTO(
                                "Benjamin",
                                "Franklin",
                                1,
                                "Politician, writer, inventor, scientist"),
                        BigDecimal.ZERO
                )
        );
        return candidatePercentageDTOS;
    }

    private static List<CandidateDTO> provideCandidateDTOList() {
        List<CandidateDTO> candidateDTOS = new ArrayList<>();
        candidateDTOS.add(
                new CandidateDTO(
                        "Bill",
                        "Gates",
                        1,
                        "Programmer, Investor, Founder of Microsoft")
        );
        candidateDTOS.add(
                new CandidateDTO(
                        "Jon",
                        "Jones",
                        2,
                        "Mixed martial arts and UFC fighter.")
        );
        return candidateDTOS;
    }

    private static List<CandidatePercentageDTO> provideCandidatePercentageDTOList() {
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
        candidatePercentageDTOS.add(
                new CandidatePercentageDTO(
                        new CandidateDTO(
                                "Jon",
                                "Jones",
                                2,
                                "Mixed martial arts and UFC fighter."),
                        BigDecimal.ZERO
                )
        );
        candidatePercentageDTOS.add(
                new CandidatePercentageDTO(
                        new CandidateDTO(
                                "Benjamin",
                                "Franklin",
                                3,
                                "Politician, writer, inventor, scientist"),
                        BigDecimal.ZERO
                )
        );
        return candidatePercentageDTOS;
    }
}