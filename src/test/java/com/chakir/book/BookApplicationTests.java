package com.chakir.book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
class BookApplicationTests {
    private static MockHttpServletRequest request;
    @PersistenceContext
    private EntityManager entityManager;


}
