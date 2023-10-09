package com.President.Election.repository;

import com.President.Election.model.Voter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoterRepository {

    List<Voter> findAll();

    Voter findByName(String name);

    Voter save (Voter voter);
}
