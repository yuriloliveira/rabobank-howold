package com.rabobank.assignment.howold.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.rabobank.assignment.howold.date.BirthdateFactory.DEFAULT_BIRTHDATE_FORMAT;


@Data
@ConfigurationProperties(prefix="rabobank.howold.data")
public class DataProperties {
    private String birthdateInputFormat = DEFAULT_BIRTHDATE_FORMAT;
}
