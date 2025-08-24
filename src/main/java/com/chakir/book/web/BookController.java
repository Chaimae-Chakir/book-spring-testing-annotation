package com.chakir.book.web;

import com.chakir.book.entity.Book;
import com.chakir.book.service.BookService;
import com.chakir.book.dto.BookRequest;
import com.chakir.book.dto.BookResponse;
import com.chakir.book.mapper.BookMapper;
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
	private final BookMapper bookMapper;

	public BookController(BookService bookService, BookMapper bookMapper) {
		this.bookService = bookService;
		this.bookMapper = bookMapper;
	}

	@PostMapping
	public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
		Book created = bookService.create(bookMapper.mapToEntity(request));
		BookResponse response = bookMapper.mapToResponse(created);
		return ResponseEntity.created(URI.create("/api/books/" + created.getId())).body(response);
	}

	@GetMapping("/{id}")
	public BookResponse get(@PathVariable Long id) {
		return bookMapper.mapToResponse(bookService.getById(id));
	}

	@GetMapping
	public List<BookResponse> list() {
		return bookService.list().stream().map(bookMapper::mapToResponse).toList();
	}

	@PutMapping("/{id}")
	public BookResponse update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
		return bookMapper.mapToResponse(bookService.update(id, bookMapper.mapToEntity(request)));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		bookService.delete(id);
	}


}


