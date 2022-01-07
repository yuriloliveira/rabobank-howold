package com.rabobank.assignment.howold.date;

import com.rabobank.assignment.howold.data.DataProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BirthdateFactoryConfig {
    @Value("${rabobank.howold.data.birthdate.input-format:yyyy-MM-dd}")
    private String birthdateInputFormat;

    @Bean
    public BirthdateFactory createBirthdateFactory() {
        return new BirthdateFactory(birthdateInputFormat);
    }
}
