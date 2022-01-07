package com.rabobank.assignment.howold.person;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
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
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd ")
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
