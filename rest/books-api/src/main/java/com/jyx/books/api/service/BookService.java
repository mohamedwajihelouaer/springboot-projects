package com.jyx.books.api.service;

import com.jyx.books.api.entity.Book;
import com.jyx.books.api.common.exception.BookException;
import com.jyx.books.api.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookException("Book with id " + id + " not found"));
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        Book existingBook = bookRepository.findById(book.getId())
                .orElseThrow(() -> new BookException("Book with id " + book.getId() + " not found"));

        existingBook.setTitle(book.getTitle());
        existingBook.setPrice(book.getPrice());
        existingBook.setPublishDate(book.getPublishDate());

        return bookRepository.save(existingBook);
    }

    public Boolean deleteById(Long id) {
        bookRepository.deleteById(id);
        return true;
    }

    public Book findBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookException("Book with isbn " + isbn + " not found"));
    }

    public boolean existsByIsbn(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    public List<Book> findByPublishDate(LocalDate date) {
        return bookRepository.findByPublishDate(date);
    }

    public List<Book> findByPublishDateBetween(LocalDate startDate, LocalDate endDate) {
        return bookRepository.findByPublishDateBetween(startDate, endDate);
    }

    public List<Book> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return bookRepository.findByPrice_ValueBetween(minPrice, maxPrice);
    }

    public List<Book> findByPriceGreaterThan(BigDecimal minPrice) {
        return bookRepository.findByPrice_ValueGreaterThan(minPrice);
    }

    public List<Book> findByPriceLessThan(BigDecimal maxPrice) {
        return bookRepository.findByPrice_ValueLessThan(maxPrice);
    }

    public List<Book> findByPriceGreaterThanEqual(BigDecimal minPrice) {
        return bookRepository.findByPrice_ValueGreaterThanEqual(minPrice);
    }

    public List<Book> findByPriceLessThanEqual(BigDecimal maxPrice) {
        return bookRepository.findByPrice_ValueLessThanEqual(maxPrice);
    }
}
