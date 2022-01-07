package com.rabobank.assignment.howold.date;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BirthdateFactoryTest {
    private final BirthdateFactory birthdateFactory = new BirthdateFactory();

    @Test
    void getBirthdate_withValidDateString_shouldReturnBirthdate() {
        LocalDate birthdate = birthdateFactory.getBirthdate("2000-01-31");
        assertAll(
            () -> assertThat(birthdate.getYear()).isEqualTo(2000),
            () -> assertThat(birthdate.getMonth()).isEqualTo(Month.JANUARY),
            () -> assertThat(birthdate.getDayOfMonth()).isEqualTo(31)
        );
    }

    @Test
    void getBirthdate_withNullDateString_shouldReturnNull() {
        assertThat(birthdateFactory.getBirthdate(null)).isNull();
    }

    @Test
    void getBirthdate_withEmptyDateString_shouldReturnNull() {
        assertThat(birthdateFactory.getBirthdate("")).isNull();
    }

    @Test
    void getBirthdate_withInvalidDate_shouldReturnNull() {
        assertThat(birthdateFactory.getBirthdate("2000-60-60")).isNull();
    }
}
