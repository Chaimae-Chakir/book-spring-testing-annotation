package com.chakir.book;

import com.chakir.book.entity.Book;
import com.chakir.book.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.*;
import org.springframework.transaction.annotation.Transactional;
import com.chakir.book.dto.BookRequest;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class BookApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    private Book testBook;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    @Transactional
    void setup() {
        testBook = new Book();
        testBook.setTitle("Clean Code");
        testBook.setAuthor("Robert C. Martin");
        testBook.setIsbn("978-0132350884");
        testBook.setPrice(new BigDecimal("45.99"));
        testBook.setPublishedDate(LocalDate.of(2008, 8, 1));
        entityManager.persist(testBook);
        entityManager.flush();
    }

    @AfterEach
    @Transactional
    void cleanup() {
        entityManager.createNativeQuery("ALTER TABLE books ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

    @Test
    void getBookById() throws Exception {
        mockMvc.perform(get("/api/books/{id}", testBook.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"));
    }

    @Test
    void updateBook() throws Exception {
        testBook.setPrice(new BigDecimal("35.99"));
        mockMvc.perform(put("/api/books/{id}", testBook.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(35.99));
    }

    @Test
    void deleteBook() throws Exception {
        assertTrue(bookRepository.findById(testBook.getId()).isPresent());
        mockMvc.perform(delete("/api/books/{id}", testBook.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void createBook() throws Exception {
        BookRequest req = new BookRequest();
        req.setTitle("Effective Java");
        req.setAuthor("Joshua Bloch");
        req.setIsbn("978-0134685991");
        req.setPrice(new BigDecimal("55.00"));
        req.setPublishedDate(LocalDate.of(2018, 1, 6));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.matchesPattern("/api/books/\\d+")))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Effective Java"))
                .andExpect(jsonPath("$.author").value("Joshua Bloch"));
    }

    @Test
    void deleteNonExistingBookReturnsNotFound() throws Exception {
        assertFalse(bookRepository.findById(0L).isPresent());
        mockMvc.perform(delete("/api/books/{id}",0))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Book not found: 0"));
    }
}

