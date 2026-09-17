package com.jyx.books.api.service;

import com.jyx.books.api.model.BookRecord;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookCsvServiceImpl implements BookCsvService {
    @Override
    public List<BookRecord> convertToCsv(File file) {

        try {
            return new CsvToBeanBuilder<BookRecord>
                    (new FileReader(file)).withType(BookRecord.class).build().parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
