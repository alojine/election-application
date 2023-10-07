package com.President.Election.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidatePercentageDTO {

    private CandidateDTO candidateDTO;

    private BigDecimal percentageOfVotes;
}
