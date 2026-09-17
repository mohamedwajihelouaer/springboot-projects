package com.jyx.books.api.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRecord {
    @CsvBindByName
    private Long id;

    @CsvBindByName
    private String title;

    @CsvBindByName
    private String isbn;

    @CsvBindByName(column = "publish_date")
    @CsvDate("yyyy-MM-dd")
    private LocalDate publishDate;

    @CsvBindByName(column = "price_value")
    private BigDecimal price;

    @CsvBindByName(column = "price_currency")
    private String currencyCode;
}
