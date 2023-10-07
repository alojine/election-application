package com.President.Election.mapper;

import com.President.Election.DTO.VoterDTO;
import com.President.Election.model.Voter;

public interface VoterMapper {
    VoterDTO voterToVoterDTO(Voter voter);
}
