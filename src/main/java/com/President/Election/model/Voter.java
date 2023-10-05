package com.President.Election.model;

import com.President.Election.Enum.Region;
import lombok.Data;

import java.util.UUID;

@Data
public class Voter {

    private UUID id;

    private String name;

    private Region region;

    private Candidate candidate;
}
