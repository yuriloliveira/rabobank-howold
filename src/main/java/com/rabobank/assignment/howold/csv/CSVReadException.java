package com.rabobank.assignment.howold.csv;

import lombok.Getter;

@Getter
public class CSVReadException extends RuntimeException {
    public CSVReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
