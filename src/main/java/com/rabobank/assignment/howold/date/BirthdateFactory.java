package com.rabobank.assignment.howold.date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class BirthdateFactory {
    @Value("rabobank.howold.data.birthdate.format")
    private String birthdateFormat;

    public LocalDate getBirthdate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(birthdateFormat);
        return LocalDate.parse(date, formatter);
    }
}
