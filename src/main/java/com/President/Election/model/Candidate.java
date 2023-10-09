package com.President.Election.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {

    private UUID id;

    private String firstname;

    private String lastname;

    private int voteNumber;

    private String agenda;
}
