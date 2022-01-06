package com.rabobank.assignment.howold.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String firstname;
    private String lastname;
    private String birthdate;
    private Integer issues;
}
