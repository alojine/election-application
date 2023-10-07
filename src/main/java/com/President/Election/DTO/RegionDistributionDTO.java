package com.President.Election.DTO;

import com.President.Election.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionDistributionDTO {

    private Region region;

    private List<CandidatePercentageDTO> candidatePercentageDTO;
}
