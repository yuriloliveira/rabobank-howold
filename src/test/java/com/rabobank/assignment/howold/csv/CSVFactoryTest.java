package com.rabobank.assignment.howold.csv;

import com.rabobank.assignment.howold.person.Person;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CSVFactoryTest {

    private final CSVFactory csvFactory = new CSVFactory();

    @Test
    @SneakyThrows
    void fromCSVStr_withValidCSVStr_shouldReturnListOfPerson() {
        ClassLoader classloader = CSVFactory.class.getClassLoader();
        InputStream is = classloader.getResourceAsStream("static/database.csv");
        String csvStr = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        List<Person> personList = csvFactory.fromCSVStr(csvStr);
        assertThat(personList).isNotEmpty();
    }
}
