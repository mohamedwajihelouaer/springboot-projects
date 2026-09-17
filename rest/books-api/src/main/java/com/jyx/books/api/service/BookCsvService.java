package com.jyx.books.api.service;

import com.jyx.books.api.model.BookRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface BookCsvService {

    List<BookRecord> convertToCsv(File file) throws FileNotFoundException;
}
