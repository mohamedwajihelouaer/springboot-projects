package com.jyx.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jyx.library.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long>{ 
	
	/**
     * Retrieve a Book wrapped in an Optional by its unique ISBN.
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * Check whether a Book exists with the given ISBN.
     */
    boolean existsByIsbn(String isbn);
}
