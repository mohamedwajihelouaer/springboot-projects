package com.jyx.books.api.repository;

import com.jyx.books.api.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByPublishDate(LocalDate date);

    List<Book> findByPublishDateBetween(LocalDate startDate, LocalDate endDate);

    List<Book> findByPrice_ValueBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Book> findByPrice_ValueGreaterThan(BigDecimal minPrice);

    List<Book> findByPrice_ValueLessThan(BigDecimal maxPrice);

    List<Book> findByPrice_ValueGreaterThanEqual(BigDecimal minPrice);

    List<Book> findByPrice_ValueLessThanEqual(BigDecimal maxPrice);
}
