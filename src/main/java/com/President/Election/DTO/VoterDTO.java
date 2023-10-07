package com.President.Election.DTO;

import com.President.Election.enums.Region;

public record VoterDTO(
        String name,
        Region region,
        CandidateDTO candidateDTO
) {
}
