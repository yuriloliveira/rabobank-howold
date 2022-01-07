package com.rabobank.assignment.howold.csv;

import com.rabobank.assignment.howold.person.Person;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class CSVFactoryTest {

    private final CSVFactory csvFactory = new CSVFactory();

    @Test
    void fromCSVStr_withNotEmptyCSV_shouldReturnNonEmptyListOfPerson() {
        var csvStr = readDefaultFile();
        List<Person> personList = csvFactory.fromCSVStr(csvStr);
        assertThat(personList).isNotEmpty();
    }

    @Test
    void fromCSVStr_withCompleteCSV_shouldReturnListOfPersonWithAllInfoFilled() {
        var csvStr = readDefaultFile();
        List<Person> personList = csvFactory.fromCSVStr(csvStr);
        personList.forEach(person -> {
            assertThat(person.getId()).isNotEmpty().isNotNull();
            assertThat(person.getFirstname()).isNotEmpty().isNotNull();
            assertThat(person.getBirthdate()).isNotEmpty().isNotNull();
            assertThat(person.getBirthdate()).isNotEmpty().isNotNull();
            assertThat(person.getIssues()).isNotNull().isGreaterThanOrEqualTo(0);
        });
    }

    @Test
    void fromCSVStr_withCSVContainingPersonWithIdEmpty_shouldReturnListOfPersonWithIdEmpty() {
        var csvStr = readFileFromTestResources("static/person-with-no-id.csv");
        List<Person> personList = csvFactory.fromCSVStr(csvStr);
        var person = personList.get(0);
        assertAll(
            () -> assertThat(person.getId()).isEmpty(),
            () -> assertThat(person.getFirstname()).isNotEmpty().isNotNull(),
            () -> assertThat(person.getLastname()).isNotEmpty().isNotNull(),
            () -> assertThat(person.getBirthdate()).isNotEmpty().isNotNull(),
            () -> assertThat(person.getIssues()).isNotNull().isGreaterThanOrEqualTo(0)
        );
    }

    @Test
    void fromCSVStr_withCSVContainingPersonWithFirstnameEmpty_shouldReturnListOfPersonWithFirstnameEmpty() {
        var csvStr = readFileFromTestResources("static/person-with-no-firstname.csv");
        List<Person> personList = csvFactory.fromCSVStr(csvStr);
        var person = personList.get(0);
        assertAll(
            () -> assertThat(person.getFirstname()).isEmpty(),
            () -> assertThat(person.getId()).isNotEmpty().isNotNull(),
            () -> assertThat(person.getLastname()).isNotEmpty().isNotNull(),
            () -> assertThat(person.getBirthdate()).isNotEmpty().isNotNull(),
            () -> assertThat(person.getIssues()).isNotNull().isGreaterThanOrEqualTo(0)
        );
    }

    @Test
    void fromCSVStr_withCSVContainingPersonWithLastnameEmpty_shouldReturnListOfPersonWithLastnameEmpty() {
        var csvStr = readFileFromTestResources("static/person-with-no-lastname.csv");
        List<Person> personList = csvFactory.fromCSVStr(csvStr);
        var person = personList.get(0);
        assertAll(
            () -> assertThat(person.getLastname()).isEmpty(),
            () -> assertThat(person.getId()).isNotEmpty().isNotNull(),
            () -> assertThat(person.getFirstname()).isNotEmpty().isNotNull(),
            () -> assertThat(person.getBirthdate()).isNotEmpty().isNotNull(),
            () -> assertThat(person.getIssues()).isNotNull().isGreaterThanOrEqualTo(0)
        );
    }

    @Test
    void fromCSVStr_withCSVContainingPersonWithBirthdateEmpty_shouldReturnListOfPersonWithBirthdateEmpty() {
        var csvStr = readFileFromTestResources("static/person-with-no-birthdate.csv");
        List<Person> personList = csvFactory.fromCSVStr(csvStr);
        var person = personList.get(0);
        assertAll(
            () -> assertThat(person.getBirthdate()).isEmpty(),
            () -> assertThat(person.getId()).isNotEmpty().isNotNull(),
            () -> assertThat(person.getFirstname()).isNotEmpty().isNotNull(),
            () -> assertThat(person.getLastname()).isNotEmpty().isNotNull(),
            () -> assertThat(person.getIssues()).isNotNull().isGreaterThanOrEqualTo(0)
        );
    }

    @Test
    void fromCSVStr_withCSVContainingPersonWithIssuesEmpty_shouldReturnListOfPersonWithIssuesEmpty() {
        var csvStr = readFileFromTestResources("static/person-with-no-issues.csv");
        List<Person> personList = csvFactory.fromCSVStr(csvStr);
        var person = personList.get(0);
        assertAll(
            () -> assertThat(person.getIssues()).isNull(),
            () -> assertThat(person.getBirthdate()).isNotEmpty().isNotNull(),
            () -> assertThat(person.getFirstname()).isNotEmpty().isNotNull(),
            () -> assertThat(person.getLastname()).isNotEmpty().isNotNull()
        );
    }

    @Test
    void fromCSVStr_withCSVContainingEmptyPerson_shouldReturnListOfPersonWithEmptyPerson() {
        var csvStr = readFileFromTestResources("static/person-with-empty-person.csv");
        List<Person> personList = csvFactory.fromCSVStr(csvStr);
        var person = personList.get(0);
        assertAll(
            () -> assertThat(person.getFirstname()).isEmpty(),
            () -> assertThat(person.getLastname()).isEmpty(),
            () -> assertThat(person.getBirthdate()).isEmpty(),
            () -> assertThat(person.getIssues()).isNull()
        );
    }

    @Test
    void fromCSVStr_withCSVWithHeaderOnly_shouldReturnEmptyListOfPerson() {
        var csvStr = readFileFromTestResources("static/header-only.csv");
        List<Person> personList = csvFactory.fromCSVStr(csvStr);
        assertThat(personList).isEmpty();
    }

    @SneakyThrows
    private String readDefaultFile() {
        return readFileFromTestResources("static/database.csv");
    }

    @SneakyThrows
    private String readFileFromTestResources(String filepath) {
        ClassLoader classloader = CSVFactoryTest.class.getClassLoader();
        InputStream is = classloader.getResourceAsStream(filepath);
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }
}
