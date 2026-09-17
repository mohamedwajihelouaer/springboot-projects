package com.jyx.books.api.service;

import com.jyx.books.api.common.exception.BookException;
import com.jyx.books.api.entity.Book;
import com.jyx.books.api.entity.Price;
import com.jyx.books.api.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book testBook;
    private Price testPrice;

    @BeforeEach
    void setUp() {
        testPrice = new Price(new BigDecimal("29.99"), "USD");
        testBook = Book.builder()
                .id(1L)
                .title("Test Book")
                .isbn("978-3-16-148410-0")
                .publishDate(LocalDate.of(2023, 1, 1))
                .price(testPrice)
                .build();
    }

    @Test
    void findBookById_WhenExists_ShouldReturnBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        Book result = bookService.findBookById(1L);

        assertNotNull(result);
        assertEquals(testBook.getId(), result.getId());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void findBookById_WhenDoesNotExist_ShouldThrowException() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        BookException exception = assertThrows(BookException.class, () -> bookService.findBookById(2L));

        assertEquals("Book with id 2 not found", exception.getMessage());
        verify(bookRepository, times(1)).findById(2L);
    }

    @Test
    void findAll_ShouldReturnListOfBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(testBook));

        List<Book> result = bookService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testBook.getTitle(), result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void addBook_ShouldReturnSavedBook() {
        when(bookRepository.save(testBook)).thenReturn(testBook);

        Book result = bookService.addBook(testBook);

        assertNotNull(result);
        assertEquals(testBook.getTitle(), result.getTitle());
        verify(bookRepository, times(1)).save(testBook);
    }

    @Test
    void updateBook_WhenExists_ShouldReturnUpdatedBook() {
        Book updatedBook = Book.builder()
                .id(1L)
                .title("Updated Title")
                .isbn("978-3-16-148410-0")
                .publishDate(LocalDate.of(2024, 1, 1))
                .price(new Price(new BigDecimal("39.99"), "USD"))
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book result = bookService.updateBook(updatedBook);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(testBook);
    }

    @Test
    void updateBook_WhenDoesNotExist_ShouldThrowException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        BookException exception = assertThrows(BookException.class, () -> bookService.updateBook(testBook));

        assertEquals("Book with id 1 not found", exception.getMessage());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void deleteById_ShouldReturnTrue() {
        doNothing().when(bookRepository).deleteById(1L);

        Boolean result = bookService.deleteById(1L);

        assertTrue(result);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void findBookByIsbn_WhenExists_ShouldReturnBook() {
        String isbn = "978-3-16-148410-0";
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(testBook));

        Book result = bookService.findBookByIsbn(isbn);

        assertNotNull(result);
        assertEquals(isbn, result.getIsbn());
        verify(bookRepository, times(1)).findByIsbn(isbn);
    }

    @Test
    void findBookByIsbn_WhenDoesNotExist_ShouldThrowException() {
        String isbn = "978-3-16-148410-1";
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());

        BookException exception = assertThrows(BookException.class, () -> bookService.findBookByIsbn(isbn));

        assertEquals("Book with isbn " + isbn + " not found", exception.getMessage());
        verify(bookRepository, times(1)).findByIsbn(isbn);
    }

    @Test
    void existsByIsbn_WhenExists_ShouldReturnTrue() {
        String isbn = "978-3-16-148410-0";
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);

        boolean result = bookService.existsByIsbn(isbn);

        assertTrue(result);
        verify(bookRepository, times(1)).existsByIsbn(isbn);
    }

    @Test
    void existsByIsbn_WhenDoesNotExist_ShouldReturnFalse() {
        String isbn = "978-3-16-148410-1";
        when(bookRepository.existsByIsbn(isbn)).thenReturn(false);

        boolean result = bookService.existsByIsbn(isbn);

        assertFalse(result);
        verify(bookRepository, times(1)).existsByIsbn(isbn);
    }

    @Test
    void findByPublishDate_ShouldReturnListOfBooks() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        when(bookRepository.findByPublishDate(date)).thenReturn(Collections.singletonList(testBook));

        List<Book> result = bookService.findByPublishDate(date);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findByPublishDate(date);
    }

    @Test
    void findByPublishDateBetween_ShouldReturnListOfBooks() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 1);
        when(bookRepository.findByPublishDateBetween(startDate, endDate)).thenReturn(Collections.singletonList(testBook));

        List<Book> result = bookService.findByPublishDateBetween(startDate, endDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findByPublishDateBetween(startDate, endDate);
    }

    @Test
    void findByPriceBetween_ShouldReturnListOfBooks() {
        BigDecimal min = new BigDecimal("10.00");
        BigDecimal max = new BigDecimal("50.00");
        when(bookRepository.findByPrice_ValueBetween(min, max)).thenReturn(Collections.singletonList(testBook));

        List<Book> result = bookService.findByPriceBetween(min, max);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findByPrice_ValueBetween(min, max);
    }

    @Test
    void findByPriceGreaterThan_ShouldReturnListOfBooks() {
        BigDecimal min = new BigDecimal("10.00");
        when(bookRepository.findByPrice_ValueGreaterThan(min)).thenReturn(Collections.singletonList(testBook));

        List<Book> result = bookService.findByPriceGreaterThan(min);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findByPrice_ValueGreaterThan(min);
    }

    @Test
    void findByPriceLessThan_ShouldReturnListOfBooks() {
        BigDecimal max = new BigDecimal("50.00");
        when(bookRepository.findByPrice_ValueLessThan(max)).thenReturn(Collections.singletonList(testBook));

        List<Book> result = bookService.findByPriceLessThan(max);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findByPrice_ValueLessThan(max);
    }

    @Test
    void findByPriceGreaterThanEqual_ShouldReturnListOfBooks() {
        BigDecimal min = new BigDecimal("29.99");
        when(bookRepository.findByPrice_ValueGreaterThanEqual(min)).thenReturn(Collections.singletonList(testBook));

        List<Book> result = bookService.findByPriceGreaterThanEqual(min);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findByPrice_ValueGreaterThanEqual(min);
    }

    @Test
    void findByPriceLessThanEqual_ShouldReturnListOfBooks() {
        BigDecimal max = new BigDecimal("29.99");
        when(bookRepository.findByPrice_ValueLessThanEqual(max)).thenReturn(Collections.singletonList(testBook));

        List<Book> result = bookService.findByPriceLessThanEqual(max);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findByPrice_ValueLessThanEqual(max);
    }
}