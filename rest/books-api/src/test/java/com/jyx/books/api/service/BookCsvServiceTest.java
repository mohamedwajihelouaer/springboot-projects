package com.jyx.books.api.service;

import com.jyx.books.api.model.BookRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookCsvServiceTest {

    @Test
    void testConvertCsv() throws FileNotFoundException {

        BookCsvService bookCsvService = new BookCsvServiceImpl();

        File file = ResourceUtils.getFile("classpath:csvdata/books.csv");

        List<BookRecord> recordList = bookCsvService.convertToCsv(file);

        System.out.println(recordList.subList(0, 5));

        assertEquals(2500, recordList.size());
    }
}
