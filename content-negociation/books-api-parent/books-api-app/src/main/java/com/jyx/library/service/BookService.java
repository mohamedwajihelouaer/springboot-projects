package com.jyx.library.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyx.library.domain.Book;
import com.jyx.library.repository.BookRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    /**
     * Create and persist a new Book.
     */
    @Transactional
    public Book createBook(Book book) {
        if (book.getId() != null) {
            throw new IllegalArgumentException("A new book cannot already have an ID");
        }
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new IllegalStateException("A book with ISBN " + book.getIsbn() + " already exists");
        }
        return bookRepository.save(book);
    }

    /**
     * Retrieve a Book by its primary key ID.
     */
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
    }

    /**
     * Retrieve a Book by its unique ISBN.
     */
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ISBN: " + isbn));
    }

    /**
     * Retrieve all books (unpaged).
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieve books with pagination and sorting.
     */
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    /**
     * Update an existing Book entity.
     */
    @Transactional
    public Book updateBook(Long id, Book updatedBookData) {
        Book existingBook = getBookById(id);

        // Verify ISBN uniqueness if the ISBN is being modified
        if (!existingBook.getIsbn().equals(updatedBookData.getIsbn()) 
                && bookRepository.existsByIsbn(updatedBookData.getIsbn())) {
            throw new IllegalStateException("A book with ISBN " + updatedBookData.getIsbn() + " already exists");
        }

        existingBook.setTitle(updatedBookData.getTitle());
        existingBook.setAuthor(updatedBookData.getAuthor());
        existingBook.setIsbn(updatedBookData.getIsbn());
        existingBook.setPrice(updatedBookData.getPrice());
        existingBook.setPages(updatedBookData.getPages());
        existingBook.setPublishedDate(updatedBookData.getPublishedDate());
        existingBook.setDescription(updatedBookData.getDescription());

        return bookRepository.save(existingBook);
    }

    /**
     * Delete a Book by its primary key ID.
     */
    @Transactional
    public void deleteBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot delete: Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}