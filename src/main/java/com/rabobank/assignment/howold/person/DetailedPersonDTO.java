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
    public Long getAge() {
        if (birthdate == null) {
            return null;
        }
        var now = LocalDate.now();
        var yearsBetween = ChronoUnit.YEARS.between(birthdate, now);
        return yearsBetween >= 0 ? yearsBetween : 0;
    }
}
