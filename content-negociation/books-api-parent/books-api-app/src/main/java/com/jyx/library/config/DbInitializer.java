package com.jyx.library.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.jyx.library.domain.Author;


import com.jyx.library.repository.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.jyx.library.domain.Book;
import com.jyx.library.repository.BookRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("!test") // Prevents automatic execution during unit/integration tests
@RequiredArgsConstructor
public class DbInitializer implements CommandLineRunner {

	private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public void run(String... args) {
        if (bookRepository.count() > 0) {
            log.info("Database already populated. Skipping initialization.");
            return;
        }

        log.info("Populating H2 database with sample book data...");

        List<Book> initialBooks = List.of(
            Book.builder()
                .title("Clean Code: A Handbook of Agile Software Craftsmanship")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .price(new BigDecimal("37.99"))
                .pages(464)
                .publishedDate(LocalDate.of(2008, 8, 1))
                .description("A guide to writing clean, maintainable, and readable code.")
                .build(),

            Book.builder()
                .title("Effective Java")
                .author("Joshua Bloch")
                .isbn("9780134685991")
                .price(new BigDecimal("45.50"))
                .pages(412)
                .publishedDate(LocalDate.of(2017, 12, 27))
                .description("Best practices for the Java platform covering core language idioms.")
                .build(),

            Book.builder()
                .title("Designing Data-Intensive Applications")
                .author("Martin Kleppmann")
                .isbn("9781449373320")
                .price(new BigDecimal("49.99"))
                .pages(616)
                .publishedDate(LocalDate.of(2017, 3, 16))
                .description("Key principles and architectures behind modern, reliable data systems.")
                .build(),

            Book.builder()
                .title("Spring in Action")
                .author("Craig Walls")
                .isbn("9781617297571")
                .price(new BigDecimal("42.00"))
                .pages(520)
                .publishedDate(LocalDate.of(2022, 3, 29))
                .description("Comprehensive guide to modern Spring and Spring Boot development.")
                .build()
        );

        bookRepository.saveAll(initialBooks);
        log.info("Successfully loaded {} books into H2 database.", initialBooks.size());

        List<Author> authors = List.of(
                Author.builder()
                        .id(1L)
                        .firstName("John")
                        .lastName("Doe")
                        .birthDate(LocalDate.of(1998, 8, 1))
                        .email("john.doe@mail.com")
                        .phone("1234567890")
                        .address("home 1")
                        .build(),
                Author.builder()
                        .id(2L)
                        .firstName("Jane")
                        .lastName("Smith")
                        .birthDate(LocalDate.of(1985, 5, 15))
                        .email("jane.smith@mail.com")
                        .phone("2345678901")
                        .address("42 Elm St, Apt 4")
                        .build(),
                Author.builder()
                        .id(3L)
                        .firstName("Michael")
                        .lastName("Johnson")
                        .birthDate(LocalDate.of(1979, 11, 20))
                        .email("m.johnson@mail.com")
                        .phone("3456789012")
                        .address("101 Maple Dr")
                        .build(),
                Author.builder()
                        .id(4L)
                        .firstName("Emily")
                        .lastName("Davis")
                        .birthDate(LocalDate.of(1992, 2, 10))
                        .email("emily.davis@mail.com")
                        .phone("4567890123")
                        .address("7B Oak Lane")
                        .build(),
                Author.builder()
                        .id(5L)
                        .firstName("William")
                        .lastName("Brown")
                        .birthDate(LocalDate.of(1968, 7, 24))
                        .email("w.brown@mail.com")
                        .phone("5678901234")
                        .address("55 Pine Blvd")
                        .build(),
                Author.builder()
                        .id(6L)
                        .firstName("Sarah")
                        .lastName("Miller")
                        .birthDate(LocalDate.of(1988, 9, 30))
                        .email("sarah.miller@mail.com")
                        .phone("6789012345")
                        .address("88 Cedar Ave")
                        .build(),
                Author.builder()
                        .id(7L)
                        .firstName("David")
                        .lastName("Wilson")
                        .birthDate(LocalDate.of(1975, 12, 5))
                        .email("david.wilson@mail.com")
                        .phone("7890123456")
                        .address("12 Birch Ct")
                        .build(),
                Author.builder()
                        .id(8L)
                        .firstName("Laura")
                        .lastName("Moore")
                        .birthDate(LocalDate.of(1995, 4, 18))
                        .email("laura.moore@mail.com")
                        .phone("8901234567")
                        .address("33 Spruce Way")
                        .build(),
                Author.builder()
                        .id(9L)
                        .firstName("James")
                        .lastName("Taylor")
                        .birthDate(LocalDate.of(1982, 1, 22))
                        .email("j.taylor@mail.com")
                        .phone("9012345678")
                        .address("909 Walnut St")
                        .build(),
                Author.builder()
                        .id(10L)
                        .firstName("Linda")
                        .lastName("Anderson")
                        .birthDate(LocalDate.of(1990, 10, 12))
                        .email("linda.anderson@mail.com")
                        .phone("0123456789")
                        .address("77 Ash Rd")
                        .build()
        );


        authorRepository.saveAll(authors);
        log.info("Successfully loaded {} authors into H2 database.", authors.size());


    }
}
