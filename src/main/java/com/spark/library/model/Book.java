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
public class Book {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty (message = "Book name cannot be empty.")
    @Size(min = 3, max = 30, message = "Please check that book name should be between 3 and 30 digits.")
    private String name;

    @NotEmpty (message = "Book author name cannot be empty.")
    @Size(min = 3, max = 30, message = "Please check that author name should be between 3 and 30 digits.")
    private String author;

    @NotEmpty (message = "Book date edition cannot be empty.")
    @Min(value = 1800, message = "Please check that book edition date. Should be more than 1800 year.")
    private int edition;


}
