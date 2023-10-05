package com.President.Election.DTO;

public record VoteDTO(
        String name,
        String region,
        int candidateNumber
) {
}
