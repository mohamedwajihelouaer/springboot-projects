package com.jyx.books.api.exception;

public class BookNotFoundException extends RuntimeException{


    public BookNotFoundException(String message) {
        super(message);
    }


}
