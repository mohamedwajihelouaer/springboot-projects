package com.jyx.books.api.repository;

import com.jyx.books.api.entity.Book;
import com.jyx.books.api.entity.Price;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TestEntityManager entityManager;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }


    @Test
    @Sql("/insert-books.sql")
    void testFindAllBooksFromScript() {
        List<Book> books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(3);
    }

    @Test
    void testFindAllBook() {
        List<Book> booksToSave = List.of(
                Book.builder()
                        .title("title")
                        .isbn(faker.code().isbn13(true))
                        .price(new Price(new BigDecimal("150.00"), faker.money().currency()))
                        .publishDate(LocalDate.of(2020, 1, 1))
                        .build(),

                Book.builder()
                        .title("title 2")
                        .isbn(faker.code().isbn13(true))
                        .price(new Price(new BigDecimal("150.00"), faker.money().currency()))
                        .publishDate(LocalDate.of(2020, 1, 1))
                        .build()
        );

        booksToSave.forEach(book -> {
            entityManager.persistAndFlush(book);
        });

        List<Book> books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(2);

    }


    @Test
    void testSaveBook() {
        Book bookToSave = Book.builder()
                .title("title")
                .isbn(faker.code().isbn13(true))
                .price(new Price(new BigDecimal("150.00"), faker.money().currency()))
                .publishDate(LocalDate.of(2020, 1, 1))
                .build();
        Book savedBook = bookRepository.save(bookToSave);

        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId() == 1L);
    }

    @Test
    @Sql("/insert-books.sql")
    void existsByIsbn() {
        boolean exists = bookRepository.existsByIsbn("9780135957059");
        assertThat(exists).isTrue();
    }

    @Test
    @Sql("/insert-books.sql")
    void findByIsbn() {
        String isbn = "9780135957059";
        Book book = bookRepository.findByIsbn(isbn).orElse(null);
        assertThat(book).isNotNull();
    }

    @Test
    void findByPrice_ValueBetween() {
    }

    @Test
    void findByPrice_ValueGreaterThan() {
    }

    @Test
    void findByPrice_ValueLessThan() {
    }

    @Test
    void findByPrice_ValueGreaterThanEqual() {
    }

    @Test
    void findByPrice_ValueLessThanEqual() {
    }

    @Test
    void findByPublishDate() {
    }

    @Test
    void findByPublishDateBetween() {
    }
}