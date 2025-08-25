package com.chakir.book.service;

import com.chakir.book.entity.Book;
import com.chakir.book.dto.BookRequest;
import com.chakir.book.dto.BookResponse;
import com.chakir.book.exception.DuplicateIsbnException;
import com.chakir.book.exception.ResourceNotFoundException;
import com.chakir.book.mapper.BookMapper;
import com.chakir.book.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {
	private final BookRepository bookRepository;
	private final BookMapper bookMapper;

	public BookService(BookRepository bookRepository, BookMapper bookMapper) {
		this.bookRepository = bookRepository;
		this.bookMapper = bookMapper;
	}

	public BookResponse create(BookRequest request) {
		Book book = bookMapper.mapToEntity(request);
		if (bookRepository.existsByIsbn(book.getIsbn())) {
			throw new DuplicateIsbnException("ISBN already exists: " + book.getIsbn());
		}
		Book savedBook = bookRepository.save(book);
		return bookMapper.mapToResponse(savedBook);
	}

	@Transactional(readOnly = true)
	public BookResponse getById(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
		return bookMapper.mapToResponse(book);
	}

	@Transactional(readOnly = true)
	public List<BookResponse> list() {
		return bookRepository.findAll().stream()
				.map(bookMapper::mapToResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public List<BookResponse> searchByAuthor(String author) {
		return bookRepository.findAllByAuthor(author).stream()
				.map(bookMapper::mapToResponse)
				.toList();
	}

	public BookResponse update(Long id, BookRequest request) {
		Book existing = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
		Book update = bookMapper.mapToEntity(request);
		
		if (!existing.getIsbn().equals(update.getIsbn()) &&
				bookRepository.existsByIsbn(update.getIsbn())) {
			throw new DuplicateIsbnException("ISBN already exists: " + update.getIsbn());
		}
		
		existing.setTitle(update.getTitle());
		existing.setAuthor(update.getAuthor());
		existing.setIsbn(update.getIsbn());
		existing.setPrice(update.getPrice());
		existing.setPublishedDate(update.getPublishedDate());
		
		Book savedBook = bookRepository.save(existing);
		return bookMapper.mapToResponse(savedBook);
	}

	public void delete(Long id) {
		Book existing = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
		bookRepository.delete(existing);
	}


}


