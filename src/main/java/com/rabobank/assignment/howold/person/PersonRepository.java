package com.rabobank.assignment.howold.person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    List<Person> findAll();
    List<Person> findAllSortedByIssues();
    Optional<Person> findPerson(String id);
}
