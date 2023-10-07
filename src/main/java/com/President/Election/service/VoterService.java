package com.President.Election.service;

import com.President.Election.enums.Region;
import com.President.Election.model.Voter;

import java.util.List;

public interface VoterService {

    Voter registerVote (Voter voter) throws Exception;

    List<Voter> getAllByRegion(Region region);

    List<Voter> getAll();
}
