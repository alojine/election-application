package com.President.Election.repository.Impl;

import com.President.Election.Enum.Region;
import com.President.Election.model.Voter;
import com.President.Election.repository.VoteRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VoteRepositoryImpl implements VoteRepository {
    private final List<Voter> voterList = new ArrayList<>();

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
