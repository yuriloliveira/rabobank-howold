package com.rabobank.assignment.howold.person;

import com.rabobank.assignment.howold.csv.CSVFactory;
import com.rabobank.assignment.howold.utils.FileUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.rabobank.assignment.howold.testutils.TestUtils.getDefaultPersonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PersonStaticListRepositoryTest {
    private final List<Person> PERSON_LIST = getDefaultPersonList();
    private final PersonStaticListRepository personStaticListRepository = new PersonStaticListRepository(PERSON_LIST);

    @Test
    void findAll_withPersonList_shouldReturnAllPersonFromList () {
        List<Person> personList = personStaticListRepository.findAll();
        assertThat(personList).hasSameElementsAs(PERSON_LIST);
    }

    @Test
    void findAll_withEmptyPersonList_shouldReturnEmptyList() {
        var emptyRepository = new PersonStaticListRepository(Collections.emptyList());
        assertThat(emptyRepository.findAll()).isEmpty();
    }

    @Test
    void findAllSortedByIssues_withNotEmptyPersonList_shouldReturnSortedPersonList() {
        List<Person> unsortedPersonList = getDefaultPersonList();
        Collections.shuffle(unsortedPersonList);
        var unsortedPersonListRepository = new PersonStaticListRepository(unsortedPersonList);
        List<Person> sortedPersonList = unsortedPersonListRepository.findAllSortedByIssues();
        var expectedIssues = unsortedPersonList
            .stream()
            .map(Person::getIssues)
            .filter(Predicate.not(Objects::isNull))
            .sorted()
            .collect(Collectors.toList());
        var sortedPersonListIssues = sortedPersonList
            .stream()
            .map(Person::getIssues)
            .filter(Predicate.not(Objects::isNull))
            .collect(Collectors.toList());
        assertThat(sortedPersonListIssues).containsExactlyElementsOf(expectedIssues);
    }

    @Test
    void findAllSortedByIssues_withListContainingNullIssues_shouldReturnNullIssuesLast() {
        List<Person> personListWithNullIssues = getDefaultPersonList();
        personListWithNullIssues.add(0, new Person("100","firstname", "lastname", "birthdate", null));
        personListWithNullIssues.add(new Person("200","firstname", "lastname", "birthdate", null));
        var personListRepository = new PersonStaticListRepository(personListWithNullIssues);
        var nullCount = (int) personListWithNullIssues.stream().filter(person -> person.getIssues() == null).count();
        var personListSortedByIssues = personListRepository.findAllSortedByIssues();
        var firstIssues = personListSortedByIssues
            .subList(0, personListSortedByIssues.size() - nullCount)
            .stream()
            .map(Person::getIssues)
            .collect(Collectors.toList());
        var lastIssues = personListSortedByIssues
            .subList(personListSortedByIssues.size() - nullCount, personListSortedByIssues.size())
            .stream()
            .map(Person::getIssues)
            .collect(Collectors.toList());
        assertAll(
            () -> assertThat(firstIssues).isNotEmpty(),
            () -> assertThat(lastIssues).isNotEmpty(),
            () -> assertThat(firstIssues).doesNotContainNull(),
            () -> assertThat(lastIssues).containsOnlyNulls()
        );
    }

    @Test
    void findAllSortedByIssues_withEmptyList_shouldReturnEmptyList() {
        var emptyPersonListRepository = new PersonStaticListRepository(Collections.emptyList());
        assertThat(emptyPersonListRepository.findAllSortedByIssues()).isEmpty();
    }

    @Test
    void findPerson_withExistingId_shouldReturnThePerson() {
        var existingPerson = PERSON_LIST.stream().filter(p -> p.getId() != null).findFirst().get();
        var foundPerson = personStaticListRepository.findPerson(existingPerson.getId());
        assertAll(
            () -> assertThat(foundPerson).isPresent(),
            () -> assertThat(foundPerson.get()).isEqualTo(existingPerson)
        );
    }

    @Test
    void findPerson_withUnexistingId_shouldReturnEmptyOptionalPerson() {
        var unexistingId = UUID.randomUUID().toString();
        var foundPerson = personStaticListRepository.findPerson(unexistingId);
        assertThat(foundPerson).isNotPresent();
    }
}
