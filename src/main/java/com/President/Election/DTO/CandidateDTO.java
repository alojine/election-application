package com.President.Election.DTO;

public record CandidateDTO(
        String firstname,
        String lastname,
        int voteNumber,
        String agenda
) {
}
