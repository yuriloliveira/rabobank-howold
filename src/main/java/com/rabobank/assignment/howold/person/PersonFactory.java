package com.rabobank.assignment.howold.person;

import com.rabobank.assignment.howold.date.BirthdateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonFactory {
    @Autowired
    private BirthdateFactory birthdateFactory;

    public Person getPerson(String name, String birthdateStr) {
        return new Person(name, birthdateStr);
    }
}
