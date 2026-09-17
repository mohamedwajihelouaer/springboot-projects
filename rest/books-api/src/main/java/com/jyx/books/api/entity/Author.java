package com.jyx.books.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 *
 * Created by MW EL OUAER
 * Validation using Hibernate validation before it hits Databse
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotBlank
    @NotNull
    @Email(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Please provide a valid email address"
    )
    private String email;

    @NotBlank
    @NotNull
    private String phone;

    @NotBlank
    @NotNull
    private String address;

    // migration test
    @Column(length = 255, nullable = false, name = "blog_url")
    private String blogUrl;

}
