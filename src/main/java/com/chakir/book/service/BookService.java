package com.chakir.book.service;

import com.chakir.book.entity.Book;
import com.chakir.book.exception.DuplicateIsbnException;
import com.chakir.book.exception.ResourceNotFoundException;
import com.chakir.book.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {
	private final BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public Book create(Book book) {
		if (bookRepository.existsByIsbn(book.getIsbn())) {
			throw new DuplicateIsbnException("ISBN already exists: " + book.getIsbn());
		}
		return bookRepository.save(book);
	}

	@Transactional(readOnly = true)
	public Book getById(Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
	}

	@Transactional(readOnly = true)
	public List<Book> list() {
		return bookRepository.findAll();
	}

	public Book update(Long id, Book update) {
		Book existing = getById(id);
		if (!existing.getIsbn().equals(update.getIsbn()) &&
				bookRepository.existsByIsbn(update.getIsbn())) {
			throw new DuplicateIsbnException("ISBN already exists: " + update.getIsbn());
		}
		existing.setTitle(update.getTitle());
		existing.setAuthor(update.getAuthor());
		existing.setIsbn(update.getIsbn());
		existing.setPrice(update.getPrice());
		existing.setPublishedDate(update.getPublishedDate());
		return existing;
	}

	public void delete(Long id) {
		Book existing = getById(id);
		bookRepository.delete(existing);
	}
}


