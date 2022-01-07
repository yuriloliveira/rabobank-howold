package com.rabobank.assignment.howold.person;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersonStaticListRepository implements PersonRepository {
    private final List<Person> personList;
    private final List<Person> personListSortedByIssues;

    public PersonStaticListRepository(List<Person> personList) {
        this.personList = personList;
        this.personListSortedByIssues = personList
            .stream()
            .filter(person -> person.getIssues() != null)
            .sorted(Comparator.comparing(Person::getIssues))
            .collect(Collectors.toList());
        this.personListSortedByIssues
            .addAll(personList.stream().filter(person -> person.getIssues() == null).collect(Collectors.toList()));
    }

    @Override
    public List<Person> findAll() {
        return personList;
    }

    @Override
    public List<Person> findAllSortedByIssues() {
        return personListSortedByIssues;
    }

    @Override
    public Optional<Person> findPerson(String id) {
        return personList
            .stream()
            .filter(person -> id.equals(person.getId()))
            .findFirst();
    }
}
