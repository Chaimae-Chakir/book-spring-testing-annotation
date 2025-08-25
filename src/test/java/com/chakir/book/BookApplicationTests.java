package com.chakir.book;

import com.chakir.book.entity.Book;
import com.chakir.book.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource("/application.properties")
public class BookApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Order(1)
    @Transactional
    void performGetBookById() throws Exception {
        Book book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setIsbn("978-0132350884");
        book.setPrice(new BigDecimal("45.99"));
        book.setPublishedDate(LocalDate.of(2008, 8, 1));
        entityManager.persist(book);
        entityManager.flush();

        mockMvc.perform(get("/api/books/{id}", book.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"));
    }

    @Test
    @Order(2)
    void createBook() throws Exception {
        Book book = new Book();
        book.setTitle("Effective Java");
        book.setAuthor("Joshua Bloch");
        book.setIsbn("978-0134685991");
        book.setPrice(new BigDecimal("54.99"));
        book.setPublishedDate(LocalDate.of(2018, 1, 6));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Effective Java"))
                .andExpect(jsonPath("$.author").value("Joshua Bloch"))
                .andExpect(jsonPath("$.isbn").value("978-0134685991"));

        Optional<Book> byIsbn = bookRepository.findByIsbn("978-0134685991");
        assertTrue(byIsbn.isPresent(), "Book with ISBN 978-0134685991 should exist");
        assertEquals("Joshua Bloch", byIsbn.get().getAuthor(), "Book author should match");
    }

    @Test
    @Order(3)
    @Transactional
    void deleteBookById() throws Exception {
        Book book = new Book();
        book.setTitle("Design Patterns: Elements of Reusable Object-Oriented Software");
        book.setAuthor("Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides");
        book.setIsbn("978-0201633610");
        book.setPrice(new BigDecimal("49.95"));
        book.setPublishedDate(LocalDate.of(1994, 10, 31));
        entityManager.persist(book);
        entityManager.flush();
        Long bookId = book.getId();

        assertTrue(bookRepository.findById(bookId).isPresent(), "Book should exist before deletion");
        mockMvc.perform(delete("/api/books/{id}", bookId))
                .andExpect(status().isNoContent());

        assertFalse(bookRepository.findById(bookId).isPresent(), "Book should be deleted");
    }
}
