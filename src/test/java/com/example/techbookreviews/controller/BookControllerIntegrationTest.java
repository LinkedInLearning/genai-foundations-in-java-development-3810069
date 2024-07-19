package com.example.techbookreviews.controller;

import com.example.techbookreviews.entity.Book;
import com.example.techbookreviews.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();

        book1 = new Book();
        book1.setTitle("Clean Code");
        book1.setAuthor("Robert C. Martin");
        book1.setIsbn("978-0132350884");

        book2 = new Book();
        book2.setTitle("Effective Java");
        book2.setAuthor("Joshua Bloch");
        book2.setIsbn("978-0134685991");

        bookRepository.save(book1);
        bookRepository.save(book2);
    }

    @Test
    void getAllBooks_ShouldReturnAllBooks() throws Exception {
        // given

        // when
        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(book1.getTitle())))
                .andExpect(jsonPath("$[1].title", is(book2.getTitle())));
    }

    @Test
    void createBook_ShouldCreateAndReturnBook() throws Exception {
        // given
        Book newBook = new Book();
        newBook.setTitle("Refactoring");
        newBook.setAuthor("Martin Fowler");
        newBook.setIsbn("978-0201485677");

        // when
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Refactoring\", \"author\": \"Martin Fowler\", \"isbn\": \"978-0201485677\" }"))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(newBook.getTitle())));

        assertThat(bookRepository.findAll()).hasSize(3);
    }

    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() throws Exception {
        // given

        // when
        mockMvc.perform(get("/books/{id}", book1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(book1.getTitle())));
    }

    @Test
    void getBookById_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        // given

        // when
        mockMvc.perform(get("/books/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found"));
    }

    @Test
    void getTopPopularBooks_ShouldReturnBooks_WhenRequested() throws Exception {
        // given

        // when
        mockMvc.perform(get("/books/popular")
                        .param("count", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(book1.getTitle())))
                .andExpect(jsonPath("$[1].title", is(book2.getTitle())));
    }
}
