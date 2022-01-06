package com.rabobank.assignment.howold.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailedPersonDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("birthdate")
    private LocalDate birthdate;
    @JsonProperty("issues")
    private Integer issues;

    @JsonProperty("age")
    public long getAge() {
        var now = LocalDate.now();
        return ChronoUnit.YEARS.between(now, birthdate);
    }
}
