package com.chakir.book.web;

import com.chakir.book.entity.Book;
import com.chakir.book.service.BookService;
import com.chakir.book.web.dto.BookRequest;
import com.chakir.book.web.dto.BookResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping
	public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
		Book created = bookService.create(mapToEntity(request));
		BookResponse response = mapToResponse(created);
		return ResponseEntity.created(URI.create("/api/books/" + created.getId())).body(response);
	}

	@GetMapping("/{id}")
	public BookResponse get(@PathVariable Long id) {
		return mapToResponse(bookService.getById(id));
	}

	@GetMapping
	public List<BookResponse> list() {
		return bookService.list().stream().map(this::mapToResponse).toList();
	}

	@PutMapping("/{id}")
	public BookResponse update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
		return mapToResponse(bookService.update(id, mapToEntity(request)));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		bookService.delete(id);
	}

	private Book mapToEntity(BookRequest request) {
		return Book.builder()
				.title(request.getTitle())
				.author(request.getAuthor())
				.isbn(request.getIsbn())
				.price(request.getPrice())
				.publishedDate(request.getPublishedDate())
				.build();
	}

	private BookResponse mapToResponse(Book book) {
		return BookResponse.builder()
				.id(book.getId())
				.title(book.getTitle())
				.author(book.getAuthor())
				.isbn(book.getIsbn())
				.price(book.getPrice())
				.publishedDate(book.getPublishedDate())
				.build();
	}
}


