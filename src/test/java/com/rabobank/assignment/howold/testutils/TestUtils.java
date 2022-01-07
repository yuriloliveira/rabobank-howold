package com.rabobank.assignment.howold.testutils;

import com.rabobank.assignment.howold.csv.CSVFactory;
import com.rabobank.assignment.howold.person.Person;
import com.rabobank.assignment.howold.person.PersonStaticListRepositoryTest;
import com.rabobank.assignment.howold.utils.FileUtils;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class TestUtils {
    @SneakyThrows
    public static List<Person> getDefaultPersonList() {
        var csvContent = FileUtils.getResourcesFileContent(
            "static/database.csv",
            PersonStaticListRepositoryTest.class.getClassLoader()
        );

        return new CSVFactory().fromCSVStr(csvContent);
    }

    public static String getExistingId() {
        Optional<String> idOptional = getDefaultPersonList().stream()
            .map(Person::getId)
            .filter(Predicate.not(Objects::isNull))
            .findFirst();

        return idOptional.orElse(null);
    }
}
