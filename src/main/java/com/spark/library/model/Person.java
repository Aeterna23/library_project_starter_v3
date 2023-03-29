package com.spark.library.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Person {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Person full name cannot be empty.")
    @Size(min = 3, message = "Name should contain at least 3 digits.")
    private String fullName;

    @Min(value = 1900, message = "Person is too old. Check the birth date please.")
    private Integer birthDate;


}
