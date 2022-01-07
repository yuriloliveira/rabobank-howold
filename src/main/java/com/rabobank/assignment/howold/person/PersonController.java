package com.rabobank.assignment.howold.person;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
@Slf4j
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<List<Person>> findAll(@RequestParam(value = "sort-by", required = false) String sortBy) {
        if (sortBy != null && sortBy.equalsIgnoreCase("issues")) {
            return personService.findAllSortedByIssues();
        }

        return personService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailedPersonDTO> findPerson(@PathVariable String id) {
        return personService.findPerson(id);
    }
}
