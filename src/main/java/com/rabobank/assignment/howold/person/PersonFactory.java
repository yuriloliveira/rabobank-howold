package com.rabobank.assignment.howold.person;

import com.rabobank.assignment.howold.date.BirthdateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonFactory {
    @Autowired
    private BirthdateFactory birthdateFactory;

    public DetailedPersonDTO getDetailedPersonDTO(Person person) {
        return DetailedPersonDTO
            .builder()
            .id(person.getId())
            .firstname(person.getFirstname())
            .lastname(person.getLastname())
            .birthdate(birthdateFactory.getBirthdate(person.getBirthdate()))
            .issues(person.getIssues())
            .build();
    }
}
