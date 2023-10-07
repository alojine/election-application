package com.President.Election.repository.Impl;

import com.President.Election.enums.Region;
import com.President.Election.model.Voter;
import com.President.Election.repository.VoterRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VoterRepositoryImpl implements VoterRepository {
    private final List<Voter> voterList = new ArrayList<>();

    @Override
    public List<Voter> findAll() {
        return voterList;
    }

    @Override
    public Voter findByNameAndRegion(String name, Region region){
        for (Voter voter : voterList) {
            if (voter.getName().equals(name) && voter.getRegion().equals(region)) {
                return voter;
            }
        }
        return null;
    }

    @Override
    public Voter save (Voter voter) {
        voterList.add(voter);
        return voter;
    }
}
