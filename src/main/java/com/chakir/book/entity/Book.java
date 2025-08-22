package com.chakir.book.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "books", uniqueConstraints = {
		@UniqueConstraint(name = "uk_book_isbn", columnNames = {"isbn"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 255)
	@Column(nullable = false, length = 255)
	private String title;

	@NotBlank
	@Size(max = 255)
	@Column(nullable = false, length = 255)
	private String author;

	@NotBlank
	@Size(min = 10, max = 17)
	@Pattern(regexp = "^[0-9\\-]+$", message = "ISBN must contain digits and optional dashes")
	@Column(nullable = false, unique = true, length = 17)
	private String isbn;

	@Positive
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@PastOrPresent
	@Column(name = "published_date")
	private LocalDate publishedDate;
}

package com.chakir.book.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "books", uniqueConstraints = {
		@UniqueConstraint(name = "uk_book_isbn", columnNames = {"isbn"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 255)
	@Column(nullable = false, length = 255)
	private String title;

	@NotBlank
	@Size(max = 255)
	@Column(nullable = false, length = 255)
	private String author;

	@NotBlank
	@Size(min = 10, max = 17)
	@Pattern(regexp = "^[0-9\\-]+$", message = "ISBN must contain digits and optional dashes")
	@Column(nullable = false, unique = true, length = 17)
	private String isbn;

	@Positive
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@PastOrPresent
	@Column(name = "published_date")
	private LocalDate publishedDate;
}


