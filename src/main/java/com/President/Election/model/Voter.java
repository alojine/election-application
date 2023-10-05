package com.President.Election.model;

import lombok.Data;

@Data
public class Voter {

    private Long id;

    private String name;

    private String region;

    private Candidate candidate;
}
