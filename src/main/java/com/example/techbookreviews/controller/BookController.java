package com.example.techbookreviews.controller;

import com.example.techbookreviews.entity.Book;
import com.example.techbookreviews.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Book>> getTopPopularBooks(@RequestParam int count) {
        List<Book> popularBooks = bookRepository.findTopPopularBooks(PageRequest.of(0, count));
        return ResponseEntity.ok(popularBooks);
    }
}
