package com.President.Election.repository;

import com.President.Election.enums.Region;
import com.President.Election.model.Voter;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository {

    Voter findByNameAndRegion(String name, Region region);

    Voter save (Voter voter);
}
