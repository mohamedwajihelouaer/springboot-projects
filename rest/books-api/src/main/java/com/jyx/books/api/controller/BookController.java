package com.jyx.books.api.controller;

import com.jyx.books.api.entity.Book;
import com.jyx.books.api.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {

        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.findBookByIsbn(isbn));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean isDeleted = bookService.deleteById(id);
        return (isDeleted) ? ResponseEntity.noContent().build() :  ResponseEntity.notFound().build();
    }

    @GetMapping("/isbn/{isbn}/exists")
    public ResponseEntity<Boolean> existsByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.existsByIsbn(isbn));
    }

    @GetMapping("/publish-date/{date}")
    public ResponseEntity<List<Book>> getByPublishDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(bookService.findByPublishDate(date));
    }

    @GetMapping("/publish-date/between")
    public ResponseEntity<List<Book>> getByPublishDateBetween(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(bookService.findByPublishDateBetween(startDate, endDate));
    }

    @GetMapping("/price/between")
    public ResponseEntity<List<Book>> getByPriceBetween(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(bookService.findByPriceBetween(minPrice, maxPrice));
    }

    @GetMapping("/price/greater-than/{minPrice}")
    public ResponseEntity<List<Book>> getByPriceGreaterThan(@PathVariable BigDecimal minPrice) {
        return ResponseEntity.ok(bookService.findByPriceGreaterThan(minPrice));
    }

    @GetMapping("/price/less-than/{maxPrice}")
    public ResponseEntity<List<Book>> getByPriceLessThan(@PathVariable BigDecimal maxPrice) {
        return ResponseEntity.ok(bookService.findByPriceLessThan(maxPrice));
    }

    @GetMapping("/price/greater-than-equal/{minPrice}")
    public ResponseEntity<List<Book>> getByPriceGreaterThanEqual(@PathVariable BigDecimal minPrice) {
        return ResponseEntity.ok(bookService.findByPriceGreaterThanEqual(minPrice));
    }

    @GetMapping("/price/less-than-equal/{maxPrice}")
    public ResponseEntity<List<Book>> getByPriceLessThanEqual(@PathVariable BigDecimal maxPrice) {
        return ResponseEntity.ok(bookService.findByPriceLessThanEqual(maxPrice));
    }
}
