package com.President.Election.model;

import com.President.Election.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voter {

    private UUID id;

    private String name;

    private Region region;

    private Candidate candidate;
}
