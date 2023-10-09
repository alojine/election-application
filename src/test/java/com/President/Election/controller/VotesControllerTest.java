package com.President.Election.controller;

import com.President.Election.DTO.CandidateDTO;
import com.President.Election.DTO.VoteDTO;
import com.President.Election.DTO.VoterDTO;
import com.President.Election.enums.Region;
import com.President.Election.exception.NotSelectableException;
import com.President.Election.mapper.VoterMapper;
import com.President.Election.model.Candidate;
import com.President.Election.model.Voter;
import com.President.Election.service.CandidateService;
import com.President.Election.service.VoterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VotesControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private CandidateService candidateService;

    @Mock
    private VoterService voterService;

    @Mock
    private VoterMapper voterMapper;

    @BeforeEach
    void setUp() throws Exception {
        when(candidateService.getByVoteNumber(anyInt())).thenReturn(provideCandidate());
        when(voterService.registerVote(any())).thenReturn(provideVoter());
        when(voterMapper.voterToVoterDTO(any())).thenReturn(provideVoterDTO());
    }

    @Test
    public void whenMakeVote_thenReturnCorrectVoterDTO() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(provideVoteDTO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void whenMakeVoteWithInvalidData_thenThrowValidationError() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("wrong body")))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void whenMakeVote_thenThrowHasVotedException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        when(voterService.registerVote(provideVoter())).thenThrow(new Exception("Not Selectable"));

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("wrong body")))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    private static VoteDTO provideVoteDTO() {
        return new VoteDTO(
                "Tomas",
                "VILNIUS",
                1
        );
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

    private static VoterDTO provideVoterDTO() {
        return new VoterDTO(
                "Tomas",
                Region.VILNIUS,
                new CandidateDTO(
                        "Bill",
                        "Gates",
                        1,
                        "Programmer, Investor, Founder of Microsoft"
                )
        );
    }

    private static Voter provideVoter() {
        return new Voter(
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
        );
    }

}