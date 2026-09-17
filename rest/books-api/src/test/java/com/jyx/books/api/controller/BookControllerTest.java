package com.jyx.books.api.controller;

import com.jyx.books.api.common.exception.BookException;
import com.jyx.books.api.entity.Book;
import com.jyx.books.api.repository.BookRepository;
import com.jyx.books.api.service.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookController.class)
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BookService bookService;

    @MockitoBean
    BookRepository bookRepository;

    @Captor
    ArgumentCaptor<Long> bookIdCaptor;

    @Captor
    ArgumentCaptor<String> bookIsbnCaptor;

    @Captor
    ArgumentCaptor<Book> bookArgumentCaptor;

    @Captor
    ArgumentCaptor<LocalDate> dateCaptor;

    @Captor
    ArgumentCaptor<LocalDate> startDateCaptor;

    @Captor
    ArgumentCaptor<LocalDate> endDateCaptor;

    @Captor
    ArgumentCaptor<BigDecimal> priceCaptor;

    @Captor
    ArgumentCaptor<BigDecimal> minPriceCaptor;

    @Captor
    ArgumentCaptor<BigDecimal> maxPriceCaptor;

    List<Book> books = new ArrayList<>();

    @BeforeEach
    void setUp() {
        books = List.of(
                Book.builder().id(1L).build(),
                Book.builder().id(3L).build()

        );
        bookRepository.saveAll(books);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testListBooks() throws Exception {

        given(bookService.findAll()).willReturn(
                List.of(
                        Book.builder().build(),
                        Book.builder().build()

                )
        );

        mockMvc.perform(get("/api/v1/books/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)));

    }

    @Test
    void testFindBookById() throws Exception {

        Book book = Book.builder().id(1L).title("Test Book").build();

        given(bookService.findBookById(book.getId())).willReturn(book);

        mockMvc.perform(get("/api/v1/books/{id}", book.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(book.getId().intValue())))
                .andExpect(jsonPath("$.title", is(book.getTitle())));

        verify(bookService).findBookById(bookIdCaptor.capture());

        assertThat(bookIdCaptor.getValue()).isEqualTo(book.getId());

    }

    @Test
    void testFindBookByIdNotFound() throws Exception {

        given(bookService.findBookById(2L)).willThrow(BookException.class);

        mockMvc.perform(get("/api/v1/books/{id}", 2L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(BookException.class, result.getResolvedException()));

    }

    @Test
    void testFindBookByIsbn() throws Exception {

        Book book = Book.builder().id(1L).title("Test Book").isbn("1234567899999").build();

        given(bookService.findBookByIsbn(book.getIsbn())).willReturn(book);

        mockMvc.perform(get("/api/v1/books/isbn/{isbn}", book.getIsbn())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(book.getId().intValue())))
                .andExpect(jsonPath("$.title", is(book.getTitle())));

        verify(bookService).findBookByIsbn(bookIsbnCaptor.capture());

        assertThat(bookIsbnCaptor.getValue()).isEqualTo(book.getIsbn());

    }

    @Test
    void testDeleteBookById() throws Exception {

        Book bookToDelete = Book.builder().id(1L).title("Test Book").build();

        given(bookService.deleteById(bookToDelete.getId())).willReturn(true);

        mockMvc.perform(delete("/api/v1/books/{id}", bookToDelete.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(bookService).deleteById(bookIdCaptor.capture());

        assertThat(bookIdCaptor.getValue()).isEqualTo(bookToDelete.getId());
    }

    @Test
    void testDeleteBookByIdNotFound() throws Exception {

        Book bookToDelete = Book.builder().id(1L).title("Test Book").build();

        given(bookService.deleteById(any(Long.class))).willReturn(false);

        mockMvc.perform(delete("/api/v1/books/{id}", bookToDelete.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(bookService).deleteById(bookIdCaptor.capture());

        assertThat(bookIdCaptor.getValue()).isEqualTo(bookToDelete.getId());
    }

    @Test
    void testAddBook() throws Exception {
        Book book = Book.builder().id(1L).title("Test Add Book").build();
        given(bookService.addBook(any(Book.class))).willReturn(book);

        mockMvc.perform(post("/api/v1/books")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(book.getTitle())));

        verify(bookService).addBook(bookArgumentCaptor.capture());
        assertThat(bookArgumentCaptor.getValue().getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    void testUpdateBook() throws Exception {
        Book book = Book.builder().id(1L).title("Test Update Book").build();
        given(bookService.updateBook(any(Book.class))).willReturn(book);

        mockMvc.perform(put("/api/v1/books")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(book.getTitle())));

        verify(bookService).updateBook(bookArgumentCaptor.capture());
        assertThat(bookArgumentCaptor.getValue().getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    void testExistsByIsbn() throws Exception {
        String isbn = "123456789";
        given(bookService.existsByIsbn(isbn)).willReturn(true);

        mockMvc.perform(get("/api/v1/books/isbn/{isbn}/exists", isbn)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(bookService).existsByIsbn(bookIsbnCaptor.capture());
        assertThat(bookIsbnCaptor.getValue()).isEqualTo(isbn);
    }

    @Test
    void testGetByPublishDate() throws Exception {
        LocalDate date = LocalDate.of(2023, 1, 1);
        given(bookService.findByPublishDate(date)).willReturn(List.of(Book.builder().title("Book 1").build()));

        mockMvc.perform(get("/api/v1/books/publish-date/{date}", date)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));

        verify(bookService).findByPublishDate(dateCaptor.capture());
        assertThat(dateCaptor.getValue()).isEqualTo(date);
    }

    @Test
    void testGetByPublishDateBetween() throws Exception {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        given(bookService.findByPublishDateBetween(startDate, endDate)).willReturn(List.of(Book.builder().title("Book 1").build()));

        mockMvc.perform(get("/api/v1/books/publish-date/between")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));

        verify(bookService).findByPublishDateBetween(startDateCaptor.capture(), endDateCaptor.capture());
        assertThat(startDateCaptor.getValue()).isEqualTo(startDate);
        assertThat(endDateCaptor.getValue()).isEqualTo(endDate);
    }

    @Test
    void testGetByPriceBetween() throws Exception {
        BigDecimal minPrice = new BigDecimal("10.00");
        BigDecimal maxPrice = new BigDecimal("20.00");
        given(bookService.findByPriceBetween(minPrice, maxPrice)).willReturn(List.of(Book.builder().title("Book 1").build()));

        mockMvc.perform(get("/api/v1/books/price/between")
                .param("minPrice", minPrice.toString())
                .param("maxPrice", maxPrice.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));

        verify(bookService).findByPriceBetween(minPriceCaptor.capture(), maxPriceCaptor.capture());
        assertThat(minPriceCaptor.getValue()).isEqualTo(minPrice);
        assertThat(maxPriceCaptor.getValue()).isEqualTo(maxPrice);
    }

    @Test
    void testGetByPriceGreaterThan() throws Exception {
        BigDecimal minPrice = new BigDecimal("10.00");
        given(bookService.findByPriceGreaterThan(minPrice)).willReturn(List.of(Book.builder().title("Book 1").build()));

        mockMvc.perform(get("/api/v1/books/price/greater-than/{minPrice}", minPrice)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));

        verify(bookService).findByPriceGreaterThan(priceCaptor.capture());
        assertThat(priceCaptor.getValue()).isEqualTo(minPrice);
    }

    @Test
    void testGetByPriceLessThan() throws Exception {
        BigDecimal maxPrice = new BigDecimal("20.00");
        given(bookService.findByPriceLessThan(maxPrice)).willReturn(List.of(Book.builder().title("Book 1").build()));

        mockMvc.perform(get("/api/v1/books/price/less-than/{maxPrice}", maxPrice)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));

        verify(bookService).findByPriceLessThan(priceCaptor.capture());
        assertThat(priceCaptor.getValue()).isEqualTo(maxPrice);
    }

    @Test
    void testGetByPriceGreaterThanEqual() throws Exception {
        BigDecimal minPrice = new BigDecimal("10.00");
        given(bookService.findByPriceGreaterThanEqual(minPrice)).willReturn(List.of(Book.builder().title("Book 1").build()));

        mockMvc.perform(get("/api/v1/books/price/greater-than-equal/{minPrice}", minPrice)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));

        verify(bookService).findByPriceGreaterThanEqual(priceCaptor.capture());
        assertThat(priceCaptor.getValue()).isEqualTo(minPrice);
    }

    @Test
    void testGetByPriceLessThanEqual() throws Exception {
        BigDecimal maxPrice = new BigDecimal("20.00");
        given(bookService.findByPriceLessThanEqual(maxPrice)).willReturn(List.of(Book.builder().title("Book 1").build()));

        mockMvc.perform(get("/api/v1/books/price/less-than-equal/{maxPrice}", maxPrice)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));

        verify(bookService).findByPriceLessThanEqual(priceCaptor.capture());
        assertThat(priceCaptor.getValue()).isEqualTo(maxPrice);
    }

}