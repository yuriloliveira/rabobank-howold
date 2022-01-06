package com.rabobank.assignment.howold;

import com.rabobank.assignment.howold.csv.CSVFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.Stream;

@SpringBootApplication
public class HowoldApplication {

	public static void main(String[] args) {
		SpringApplication.run(HowoldApplication.class, args);
	}

}
