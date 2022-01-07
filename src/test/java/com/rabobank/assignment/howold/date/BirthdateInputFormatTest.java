package com.rabobank.assignment.howold.date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(properties="rabobank.howold.data.birthdate.input-format=dd-MM-yyyy")
public class BirthdateInputFormatTest {
    @Autowired
    private BirthdateFactory birthdateFactory;

    @Test
    void getBirthdate_withNonDefaultInputFormat_shouldParseBirthdate() {
        LocalDate birthdate = birthdateFactory.getBirthdate("30-01-2000");
        assertAll(
            () -> assertThat(birthdate.getDayOfMonth()).isEqualTo(30),
            () -> assertThat(birthdate.getMonth()).isEqualTo(Month.JANUARY),
            () -> assertThat(birthdate.getYear()).isEqualTo(2000)
        );
    }
}
