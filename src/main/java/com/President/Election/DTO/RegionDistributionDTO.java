package com.President.Election.DTO;

import com.President.Election.enums.Region;
import lombok.Data;

import java.util.List;

@Data
public class RegionDistributionDTO {

    private Region region;

    private List<CandidatePercentageDTO> candidatePercentageDTO;
}
