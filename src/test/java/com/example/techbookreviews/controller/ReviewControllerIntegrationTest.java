package com.example.techbookreviews.controller;

import com.example.techbookreviews.entity.Book;
import com.example.techbookreviews.entity.Review;
import com.example.techbookreviews.repository.BookRepository;
import com.example.techbookreviews.repository.ReviewRepository;
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
class ReviewControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private Book book;
    private Review review1;
    private Review review2;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
        bookRepository.deleteAll();

        book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setIsbn("978-0132350884");
        bookRepository.save(book);

        review1 = new Review();
        review1.setReviewerName("Alice");
        review1.setContent("Great book on clean coding practices.");
        review1.setRating(5);
        review1.setBook(book);
        reviewRepository.save(review1);

        review2 = new Review();
        review2.setReviewerName("Bob");
        review2.setContent("A must-read for Java developers.");
        review2.setRating(4);
        review2.setBook(book);
        reviewRepository.save(review2);
    }

    @Test
    void getAllReviews_ShouldReturnAllReviews() throws Exception {
        // given

        // when
        mockMvc.perform(get("/books/{bookId}/reviews", book.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].reviewerName", is(review1.getReviewerName())))
                .andExpect(jsonPath("$[1].reviewerName", is(review2.getReviewerName())));
    }

    @Test
    void addReview_ShouldCreateAndReturnReview() throws Exception {
        // given
        Review newReview = new Review();
        newReview.setReviewerName("Charlie");
        newReview.setContent("Excellent book.");
        newReview.setRating(5);

        // when
        mockMvc.perform(post("/books/{bookId}/reviews", book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"reviewerName\": \"Charlie\", \"content\": \"Excellent book.\", \"rating\": 5 }"))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewerName", is(newReview.getReviewerName())));

        assertThat(reviewRepository.findAll()).hasSize(3);
    }

    @Test
    void getAverageRating_ShouldReturnAverageRating() throws Exception {
        // given

        // when
        mockMvc.perform(get("/books/{bookId}/reviews/average-rating", book.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(content().string("4.5"));
    }

    @Test
    void getAverageRating_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        // given

        // when
        mockMvc.perform(get("/books/{bookId}/reviews/average-rating", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found"));
    }
}
