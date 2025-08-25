package com.chakir.book.web;

import com.chakir.book.service.BookService;
import com.chakir.book.dto.BookRequest;
import com.chakir.book.dto.BookResponse;
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
		BookResponse response = bookService.create(request);
		return ResponseEntity.created(URI.create("/api/books/" + response.getId())).body(response);
	}

	@GetMapping("/{id}")
	public BookResponse get(@PathVariable Long id) {
		return bookService.getById(id);
	}

	@GetMapping
	public List<BookResponse> list() {
		return bookService.list();
	}

	@GetMapping("/search")
	public List<BookResponse> search(@RequestParam String author) {
		return bookService.searchByAuthor(author);
	}

	@PutMapping("/{id}")
	public BookResponse update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
		return bookService.update(id, request);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		bookService.delete(id);
	}


}


