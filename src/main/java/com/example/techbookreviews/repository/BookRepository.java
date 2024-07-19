package com.example.techbookreviews.repository;

import com.example.techbookreviews.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {


    @Query("SELECT b FROM Book b LEFT JOIN b.reviews r GROUP BY b ORDER BY COUNT(r) DESC")
    List<Book> findTopPopularBooks(Pageable pageable);

}
