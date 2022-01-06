package com.rabobank.assignment.howold.csv;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.rabobank.assignment.howold.person.Person;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class CSVFactory {
     List<Person> fromFilename(String filename) {
         try {
             String csvContent = Files.readString(Path.of(filename));
             return this.fromCSVStr(csvContent);
         } catch (IOException ex) {
             throw new CSVReadException("An IOException occured", ex);
         }
     }

    List<Person> fromCSVStr(String csvContent) {
        try {
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema headerSchema = CsvSchema.emptySchema().withHeader();
            MappingIterator<Person> reader = csvMapper
                    .readerFor(Person.class)
                    .with(headerSchema)
                    .readValues(csvContent);
            return reader.readAll();
        } catch (IOException ex) {
            throw new CSVReadException("An IOException occured", ex);
        }
    }
}
