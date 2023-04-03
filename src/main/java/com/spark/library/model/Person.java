package com.spark.library.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Table;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(appliesTo = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Person full name cannot be empty.")
    @Size(min = 3, message = "Name should contain at least 3 digits.")
    @Column(name = "fullname")
    private String fullName;

    @Column(name = "birthdate")
    @Min(value = 1900, message = "Person is too old. Check the birth date please.")
    private Integer birthDate;

    @OneToMany(mappedBy = "owner",cascade = {CascadeType.PERSIST})
    private List<Book> books;

}
