package com.rabobank.assignment.howold.date;

import com.rabobank.assignment.howold.data.DataProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BirthdateFactory {
    private final DateTimeFormatter formatter;

    public static String DEFAULT_BIRTHDATE_FORMAT = "yyyy-MM-dd";

    public BirthdateFactory(String inputDateFormat) {
        this.formatter = DateTimeFormatter.ofPattern(inputDateFormat);
    }

    public BirthdateFactory() {
        this(DEFAULT_BIRTHDATE_FORMAT);
    }

    public LocalDate getBirthdate(String date) {
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeException | NullPointerException ex) {
            return null;
        }
    }
}
