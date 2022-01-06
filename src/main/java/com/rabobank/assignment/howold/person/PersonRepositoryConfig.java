package com.rabobank.assignment.howold.person;

import com.rabobank.assignment.howold.csv.CSVFactory;
import com.rabobank.assignment.howold.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

@Configuration
public class PersonRepositoryConfig {
    @Autowired
    private CSVFactory csvFactory;

    @Bean
    public PersonRepository createPersonCSVRepository() throws IOException {
        List<Person> personList =
                csvFactory.fromCSVStr(FileUtils.getResourcesFileContent("static/database.csv"));
        return new PersonStaticListRepository(personList);
    }
}
