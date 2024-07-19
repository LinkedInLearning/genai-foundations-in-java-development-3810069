package com.example.techbookreviews.service;

import com.example.techbookreviews.entity.Book;
import com.example.techbookreviews.entity.Review;
import com.example.techbookreviews.exception.ResourceNotFoundException;
import com.example.techbookreviews.repository.BookRepository;
import com.example.techbookreviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public BookService(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public List<Review> getAllReviews(Long bookId) {
        Book book = getBookById(bookId);
        return book.getReviews();
    }

    public Review addReview(Long bookId, Review review) {
        Book book = getBookById(bookId);
        review.setBook(book);
        return reviewRepository.save(review);
    }

    public Double getAverageRating(Long bookId) {
        return reviewRepository.findAverageRatingByBookId(bookId);
    }

    public List<Book> getTopPopularBooks(int count) {
        return bookRepository.findTopPopularBooks(PageRequest.of(0, count));
    }
}
