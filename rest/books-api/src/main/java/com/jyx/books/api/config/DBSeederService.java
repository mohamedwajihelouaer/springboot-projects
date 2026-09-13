package com.jyx.books.api.config;

import com.jyx.books.api.entity.Book;
import com.jyx.books.api.entity.Price;
import com.jyx.books.api.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class DBSeederService {


    @Bean
    public CommandLineRunner init(BookRepository bookRepository) {

        return args -> {
            Faker faker = new Faker();

            Set<Book>  bookSet = new HashSet<>();

            Price uniquePrice = new Price(
                    // Generate a random price between 5.00 and 150.00
                    BigDecimal.valueOf(faker.number().randomDouble(2, 5, 150)),
                    faker.money().currencyCode()
            );
            Book uniqueBook = Book.builder()
                    .title(faker.book().title())
                    // Appending the loop index guarantees 100% uniqueness for your ISBN business key
                    .isbn(faker.code().isbn13(true))
                    .publishDate(LocalDate.now().minusDays(faker.number().numberBetween(1, 10000)))
                    .price(uniquePrice)
                    .build();



            while (bookSet.size() < 1000) {
                Price price = new Price(
                        // Generate a random price between 5.00 and 150.00
                        BigDecimal.valueOf(faker.number().randomDouble(2, 5, 150)),
                        faker.money().currencyCode()
                );
                Book book = Book.builder()
                        .title(faker.book().title())
                        // Appending the loop index guarantees 100% uniqueness for your ISBN business key
                        .isbn(faker.code().isbn13(true))
                        .publishDate(LocalDate.now().minusDays(faker.number().numberBetween(1, 10000)))
                        .price(price)
                        .build();

                bookSet.add(book);
            }

            bookRepository.saveAll(bookSet);
            log.info("Database successfully seeded. {}", bookSet.size());



        };
    }
}
