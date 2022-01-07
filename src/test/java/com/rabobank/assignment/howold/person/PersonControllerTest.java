package com.rabobank.assignment.howold.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonFactory personFactory;

    private ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    @Test
    void getPeople_withNoSortByParam_respondsWithStatusOkAndJsonBody() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/people");
        mockMvc
            .perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().string(mapper.writeValueAsString(personRepository.findAll())));
    }

    @Test
    void getPeople_withSortByIssues_respondsWithStatusOkAndJsonBody() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/people?sort-by=issues");
        mockMvc
            .perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().string(mapper.writeValueAsString(personRepository.findAllSortedByIssues())));
    }

    @Test
    void getPeople_withExistingPersonId_respondsWithStatusOkAndJsonBody() throws Exception {
        var personWithNonNullId=
            personRepository.findAll().stream().filter(person -> person.getId() != null).findFirst().get();
        var detailedPerson = personFactory.getDetailedPersonDTO(personWithNonNullId);
        var requestBuilder = MockMvcRequestBuilders.get("/people/" + personWithNonNullId.getId());
        mockMvc
            .perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().string(mapper.writeValueAsString(detailedPerson)));
    }

    @Test
    void getPeople_withNonExistingPersonId_respondsWithStatusNotFound() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/people/" + UUID.randomUUID());
        mockMvc
            .perform(requestBuilder)
            .andExpect(status().isNotFound());
    }
}
