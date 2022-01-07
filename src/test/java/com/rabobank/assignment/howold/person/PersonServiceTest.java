package com.rabobank.assignment.howold.person;

import com.rabobank.assignment.howold.error.InvalidRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.rabobank.assignment.howold.testutils.TestUtils.getDefaultPersonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;
    @Mock
    private PersonFactory personFactory;
    @InjectMocks
    private PersonService personService;

    private static final List<Person> DEFAULT_PERSON_LIST = getDefaultPersonList();
    private static final String PERSON_ID = "PERSON_ID";

    @Test
    void findAll_withNonEmptyList_shouldReturnResponseEntityStatusOk() {
        when(personRepository.findAll()).thenReturn(DEFAULT_PERSON_LIST);
        var personListResponse = personService.findAll();
        assertThat(personListResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findAll_withNonEmptyList_shouldReturnResponseEntityWithNonEmptyListBody() {
        var expectedPersonList = DEFAULT_PERSON_LIST;
        when(personRepository.findAll()).thenReturn(expectedPersonList);
        var personListResponse = personService.findAll();
        assertThat(personListResponse.getBody()).containsExactlyElementsOf(expectedPersonList);
    }

    @Test
    void findAll_withEmptyList_shouldReturnResponseEntityWithStatusOk() {
        when(personRepository.findAll()).thenReturn(Collections.emptyList());
        var personListResponse = personService.findAll();
        assertThat(personListResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findAll_withEmptyList_shouldReturnResponseEntityWithEmptyListBody() {
        when(personRepository.findAll()).thenReturn(Collections.emptyList());
        var personListResponse = personService.findAll();
        assertThat(personListResponse.getBody()).isEmpty();
    }

    @Test
    void findAllSortedByIssues_withNonEmptyList_shouldReturnResponseEntityStatusOk() {
        when(personRepository.findAllSortedByIssues()).thenReturn(DEFAULT_PERSON_LIST);
        var personListResponse = personService.findAllSortedByIssues();
        assertThat(personListResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findAllSortedByIssues_withNonEmptyList_shouldReturnResponseEntityWithNonEmptyListBody() {
        var expectedPersonList = DEFAULT_PERSON_LIST;
        when(personRepository.findAllSortedByIssues()).thenReturn(expectedPersonList);
        var personListResponse = personService.findAllSortedByIssues();
        assertThat(personListResponse.getBody()).containsExactlyElementsOf(expectedPersonList);
    }

    @Test
    void findAllSortedByIssues_withEmptyList_shouldReturnResponseEntityWithStatusOk() {
        when(personRepository.findAllSortedByIssues()).thenReturn(Collections.emptyList());
        var personListResponse = personService.findAllSortedByIssues();
        assertThat(personListResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findAllSortedByIssues_withEmptyList_shouldReturnResponseEntityWithEmptyListBody() {
        when(personRepository.findAllSortedByIssues()).thenReturn(Collections.emptyList());
        var personListResponse = personService.findAllSortedByIssues();
        assertThat(personListResponse.getBody()).isEmpty();
    }

    @Test
    void findPerson_withFoundPerson_shouldReturnResponseEntityStatusOk() {
        var person = DEFAULT_PERSON_LIST.get(0);
        DetailedPersonDTO expectedResponseBody = buildDetailedPerson();
        when(personRepository.findPerson(PERSON_ID)).thenReturn(Optional.of(person));
        when(personFactory.getDetailedPersonDTO(person)).thenReturn(expectedResponseBody);
        var personResponse = personService.findPerson(PERSON_ID);
        assertThat(personResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findPerson_withFoundPerson_shouldReturnResponseEntityWithFoundPersonBody() {
        var person = DEFAULT_PERSON_LIST.get(0);
        DetailedPersonDTO expectedResponseBody = buildDetailedPerson();
        when(personRepository.findPerson(PERSON_ID)).thenReturn(Optional.of(person));
        when(personFactory.getDetailedPersonDTO(person)).thenReturn(expectedResponseBody);
        var personResponse = personService.findPerson(PERSON_ID);
        assertThat(personResponse.getBody()).isEqualTo(expectedResponseBody);
    }

    @Test
    void findPerson_withIdNull_shouldReturnThrowIllegalArgumentException() {
        var thrown = assertThrows(InvalidRequestException.class, () -> {
            personService.findPerson(null);
        });
        assertThat(thrown).hasMessage(PersonService.ID_NOT_NULL_MESSAGE);
    }

    private DetailedPersonDTO buildDetailedPerson() {
        return DetailedPersonDTO.builder().id(PERSON_ID).build();
    }
}
