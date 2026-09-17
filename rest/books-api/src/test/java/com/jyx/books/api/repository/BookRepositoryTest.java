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
    @Sql("/insert-books.sql")
    void findByPrice_ValueBetween() {
        var minPrice = new BigDecimal("54.00");
        var maxPrice = new BigDecimal("56.00");

        List<Book> books = bookRepository.findByPrice_ValueBetween(minPrice, maxPrice);
        assertThat(books.size()).isEqualTo(1);
        assertThat(books.getFirst().getIsbn()).isEqualTo("9780134685991");
    }

    @Test
    @Sql("/insert-books.sql")
    void findByPrice_ValueGreaterThan() {
        var minPrice = new BigDecimal("54.00");

        List<Book> books = bookRepository.findByPrice_ValueGreaterThan(minPrice);
        assertThat(books.size()).isEqualTo(1);
        assertThat(books.getFirst().getIsbn()).isEqualTo("9780134685991");
    }

    @Test
    @Sql("/insert-books.sql")
    void findByPrice_ValueLessThan() {
        var minPrice = new BigDecimal("56.00");
        List<Book> books = bookRepository.findByPrice_ValueLessThan(minPrice);
        assertThat(books.size()).isEqualTo(3);
    }

    @Test
    @Sql("/insert-books.sql")
    void findByPrice_ValueGreaterThanEqual() {
        var minPrice = new BigDecimal("55.00");
        List<Book> books = bookRepository.findByPrice_ValueGreaterThanEqual(minPrice);
        assertThat(books.size()).isEqualTo(1);
    }

    @Test
    @Sql("/insert-books.sql")
    void findByPrice_ValueLessThanEqual() {
        var minPrice = new BigDecimal("55.00");
        List<Book> books = bookRepository.findByPrice_ValueLessThanEqual(minPrice);
        assertThat(books.size()).isEqualTo(3);
    }

    @Test
    @Sql("/insert-books.sql")
    void findByPublishDate() {
        LocalDate publishDate = LocalDate.of(2017, 12, 27);
        List<Book> books = bookRepository.findByPublishDate(publishDate);
        assertThat(books.size()).isEqualTo(1);
    }

    @Test
    @Sql("/insert-books.sql")
    void findByPublishDateBetween() {
        LocalDate earliestPublishDate = LocalDate.of(1992, 12, 17);
        LocalDate latestPublishDate = LocalDate.of(2019, 12, 17);

        List<Book> books = bookRepository.findByPublishDateBetween(earliestPublishDate, latestPublishDate);
        assertThat(books.size()).isEqualTo(3);
    }
}