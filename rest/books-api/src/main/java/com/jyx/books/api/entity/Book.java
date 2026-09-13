package com.jyx.books.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

import java.time.LocalDate;
import java.util.Objects;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*bean validation constraint vs hibernate entity constraint */

    @NotNull
    @NotBlank
    @Size(min = 1, max = 200)
    private String title;

    @ISBN(type =  ISBN.Type.ISBN_13)
    @Column(unique = true)
    private String isbn;

    @NotNull
    private LocalDate publishDate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "price_value", precision = 10, scale = 2)),
            @AttributeOverride(name = "currency", column = @Column(name = "price_currency"))
    })
    private Price price;

    // @Version
    // private Long version;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book other)) return false;
        return isbn != null && isbn.equals(other.getIsbn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

}
