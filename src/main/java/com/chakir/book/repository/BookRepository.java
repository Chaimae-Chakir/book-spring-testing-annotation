package com.chakir.book.repository;

import com.chakir.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
	boolean existsByIsbn(String isbn);
	Optional<Book> findByIsbn(String isbn);

	List<Book> findAllByAuthor(String author);
}


