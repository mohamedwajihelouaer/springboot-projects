package com.jyx.books.api.config;

import com.jyx.books.api.entity.Book;
import com.jyx.books.api.entity.Price;
import com.jyx.books.api.model.BookRecord;
import com.jyx.books.api.repository.BookRepository;
import com.jyx.books.api.service.BookCsvService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DBSeederService {

    private final BookCsvService bookCsvService;

    @Bean
    public CommandLineRunner init(BookRepository bookRepository) throws FileNotFoundException {


        return args -> {


                bookRepository.deleteAll();

                File file = ResourceUtils.getFile("classpath:csvdata/books.csv");
                List<BookRecord> records = bookCsvService.convertToCsv(file);

                List<Book> db = records.stream().map(bookRecord -> Book.builder()
//                        .id(bookRecord.getId())
                        .title(bookRecord.getTitle())
                        .publishDate(bookRecord.getPublishDate())
                        .isbn(bookRecord.getIsbn())
                        .price(new Price(bookRecord.getPrice(), bookRecord.getCurrencyCode()))
                        .build()).toList();

                bookRepository.saveAll(db);




            Faker faker = new Faker();

            Set<Book>  bookSet = new HashSet<>();

            Price uniquePrice = new Price(generatePrice(faker), "EUR");
            Book uniqueBook = Book.builder()
                    .title(faker.book().title())
                    .isbn("979-0-66666-777-8")
                    .publishDate(LocalDate.now().minusDays(faker.number().numberBetween(1, 10000)))
                    .price(uniquePrice)
                    .build();

            if (!bookRepository.existsByIsbn("979-0-66666-777-8")) {
                Book bookForTest = bookRepository.save(uniqueBook);
                log.info("Book for test has been saved: {}", bookForTest);
            } else {
                log.info("Book for test already exists. Skipping insertion.");
            }


            while (bookSet.size() < 3) {
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


                if (! bookRepository.existsByIsbn(book.getIsbn())) {
                    bookSet.add(book);
                }
            }

            bookRepository.saveAll(bookSet);
            log.info("Database successfully seeded. {}", bookSet.size());



        };
    }

    private BigDecimal generatePrice(Faker faker) {
        // Generate a random price between 5.00 and 150.00
        return BigDecimal.valueOf(faker.number().randomDouble(2, 5, 150));
    }


}
