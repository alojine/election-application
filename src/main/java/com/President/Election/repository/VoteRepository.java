package com.President.Election.repository;

import com.President.Election.enums.Region;
import com.President.Election.model.Voter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository {

    List<Voter> findAll();

    Voter findByNameAndRegion(String name, Region region);

    Voter save (Voter voter);
}
