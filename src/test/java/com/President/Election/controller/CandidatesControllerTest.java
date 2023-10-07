package com.President.Election.controller;

import com.President.Election.DTO.CandidateDTO;
import com.President.Election.mapper.CandidateMapper;
import com.President.Election.model.Candidate;
import com.President.Election.service.CandidateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CandidatesControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private CandidateService candidateService;

    @Mock
    private CandidateMapper candidateMapper;

    @Test
    public void whenGetAllCandidates_thenReturnCorrectCandidateList() throws Exception {
        when(candidateService.getAll()).thenReturn(provideCandidateList());
        when(candidateMapper.candidatesToCandidateDTOs(provideCandidateList())).thenReturn(provideCandidateDTOList());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/candidates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstname").value("Bill"))
                .andExpect(jsonPath("$[0].lastname").value("Gates"))
                .andExpect(jsonPath("$[0].voteNumber").value(1))
                .andExpect(jsonPath("$[0].agenda").value("Programmer, Investor, Founder of Microsoft"))
                .andReturn();
    }

    private static List<Candidate> provideCandidateList() {
        List<Candidate> candidates = new ArrayList<>();
        Candidate candidateBill = new Candidate(
                UUID.randomUUID(),
                "Bill",
                "Gates",
                1,
                "Programmer, Investor, Founder of Microsoft");
        candidates.add(candidateBill);
        return candidates;
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
        return candidateDTOS;
    }
}