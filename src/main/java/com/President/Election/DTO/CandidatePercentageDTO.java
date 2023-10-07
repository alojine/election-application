package com.President.Election.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CandidatePercentageDTO {

    private CandidateDTO candidateDTO;

    private BigDecimal percentageOfVotes;
}
