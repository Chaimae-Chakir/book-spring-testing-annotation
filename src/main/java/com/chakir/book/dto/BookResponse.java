package com.chakir.book.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class BookResponse {
	Long id;
	String title;
	String author;
	String isbn;
	BigDecimal price;
	LocalDate publishedDate;
}


