package com.President.Election.model;

import lombok.Data;

@Data
public class Candidate {

    private Long id;

    private String firstname;

    private String lastname;

    private String agenda;
}
