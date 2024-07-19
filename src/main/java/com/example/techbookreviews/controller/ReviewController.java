package com.example.techbookreviews.controller;

import com.example.techbookreviews.entity.Review;
import com.example.techbookreviews.exception.ResourceNotFoundException;
import com.example.techbookreviews.repository.BookRepository;
import com.example.techbookreviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books/{bookId}/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Review> getAllReviews(@PathVariable Long bookId) {
        return bookRepository.findById(bookId)
                .map(book -> book.getReviews())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@PathVariable Long bookId, @RequestBody Review review) {
        return bookRepository.findById(bookId)
                .map(book -> {
                    review.setBook(book);
                    return ResponseEntity.ok(reviewRepository.save(review));
                }).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    @GetMapping("/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long bookId) {
        return bookRepository.findById(bookId)
                .map(book -> {
                    Double averageRating = reviewRepository.findAverageRatingByBookId(bookId);
                    return ResponseEntity.ok(averageRating);
                }).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }
}
