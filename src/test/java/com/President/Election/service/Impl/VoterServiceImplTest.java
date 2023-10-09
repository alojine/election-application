package com.President.Election.service.Impl;

import com.President.Election.enums.Region;
import com.President.Election.exception.HasVotedException;
import com.President.Election.model.Candidate;
import com.President.Election.model.Voter;
import com.President.Election.repository.VoterRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class VoterServiceImplTest {

    @InjectMocks
    private VoterServiceImpl voterService;

    @Mock
    private VoterRepository voterRepository;

    @Test
    public void whenGetAll_thenReturnAllVoters() {
        when(voterRepository.findAll()).thenReturn(provideVoterList());

        assertThat(voterService.getAll()).isEqualTo(provideVoterList());
    }

    @Test
    public void whenGetAllByRegion_givenCorrectRegion_thenReturnAllRegionVoters() {
        when(voterRepository.findAll()).thenReturn(provideVoterList());
        Region correctRegion = Region.VILNIUS;

        assertThat(voterService.getAllByRegion(correctRegion)).isEqualTo(provideVoterList());
    }

    @Test
    public void whenGetAllByRegion_givenBadRegion_thenReturnNoVoters() {
        when(voterRepository.findAll()).thenReturn(provideVoterList());

        Region wrongRegion = Region.PANEVEZYS;
        List<Voter> emptyVoterList = new ArrayList<>();

        assertThat(voterService.getAllByRegion(wrongRegion)).isEqualTo(emptyVoterList);
    }

    @Test
    public void whenRegisterVote_thenReturnNoVoter() throws Exception {
        when(voterRepository.findByName(any())).thenReturn(null);
        when(voterRepository.save(any())).thenReturn(provideVoter());

        assertThat(voterService.registerVote(provideVoter())).isEqualTo(provideVoter());
    }

    @Test
    public void whenRegisterVote_votingForDifferentCandidate_thenThrowHasVotedException() {
        when(voterRepository.findByName(any())).thenReturn(provideVoter());
        when(voterRepository.save(any())).thenReturn(provideDifferentCandidateVoter());

        assertThrows(HasVotedException.class, () -> voterService.registerVote(provideVoter()));
    }

    @Test
    public void whenRegisterVote_votingTwice_thenThrowHasVotedException() {
        when(voterRepository.findByName(any())).thenReturn(provideVoter());
        when(voterRepository.save(any())).thenReturn(provideVoter());

        assertThrows(HasVotedException.class, () -> voterService.registerVote(provideVoter()));
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

    private static Voter provideDifferentCandidateVoter() {
        return new Voter(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                "Tomas",
                Region.VILNIUS,
                new Candidate(
                        UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                        "Jon",
                        "Jones",
                        2,
                        "Mixed martial arts and UFC fighter."
                )
        );
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
}