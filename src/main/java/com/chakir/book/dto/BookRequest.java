package com.chakir.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BookRequest {
	@NotBlank
	@Size(max = 255)
	private String title;

	@NotBlank
	@Size(max = 255)
	private String author;

	@NotBlank
	@Size(min = 10, max = 17)
	@Pattern(regexp = "^[0-9\\-]+$", message = "ISBN must contain digits and optional dashes")
	private String isbn;

	@Positive
	private BigDecimal price;

	@PastOrPresent
	private LocalDate publishedDate;
}


