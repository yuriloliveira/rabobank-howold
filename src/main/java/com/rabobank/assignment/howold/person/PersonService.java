package com.rabobank.assignment.howold.person;

import com.rabobank.assignment.howold.error.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonFactory personFactory;

    public static final String ID_NOT_NULL_MESSAGE = "Person id cannot be null";
    public ResponseEntity<List<Person>> findAll() {
        var personList = personRepository.findAll();
        return ResponseEntity.ok().body(personList);
    }

    public ResponseEntity<List<Person>> findAllSortedByIssues() {
        var personList = personRepository.findAllSortedByIssues();
        return ResponseEntity.ok().body(personList);
    }

    public ResponseEntity<DetailedPersonDTO> findPerson(String id) {
        if (id == null) {
            throw new InvalidRequestException(ID_NOT_NULL_MESSAGE);
        }

        Optional<DetailedPersonDTO> foundPerson =
            personRepository.findPerson(id).map(personFactory::getDetailedPersonDTO);
        return ResponseEntity.of(foundPerson);
    }
}
