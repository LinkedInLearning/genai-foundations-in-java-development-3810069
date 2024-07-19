package com.example.techbookreviews.service;

import com.example.techbookreviews.entity.Book;
import com.example.techbookreviews.exception.ResourceNotFoundException;
import com.example.techbookreviews.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Clean Code");
        book1.setAuthor("Robert C. Martin");
        book1.setIsbn("978-0132350884");

        book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Effective Java");
        book2.setAuthor("Joshua Bloch");
        book2.setIsbn("978-0134685991");
    }

    @Test
    void getAllBooks_ShouldReturnAllBooks() {
        // given
        given(bookRepository.findAll()).willReturn(Arrays.asList(book1, book2));

        // when
        List<Book> books = bookService.getAllBooks();

        // then
        assertThat(books).hasSize(2);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void createBook_ShouldReturnCreatedBook() {
        // given
        given(bookRepository.save(book1)).willReturn(book1);

        // when
        Book createdBook = bookService.createBook(book1);

        // then
        assertThat(createdBook).isEqualTo(book1);
        verify(bookRepository, times(1)).save(book1);
    }

    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() {
        // given
        given(bookRepository.findById(1L)).willReturn(Optional.of(book1));

        // when
        Book foundBook = bookService.getBookById(1L);

        // then
        assertThat(foundBook).isEqualTo(book1);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_ShouldThrowException_WhenBookDoesNotExist() {
        // given
        given(bookRepository.findById(1L)).willReturn(Optional.empty());

        // when
        Throwable thrown = catchThrowable(() -> bookService.getBookById(1L));

        // then
        assertThat(thrown).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Book not found");
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getTopPopularBooks_ShouldReturnBooks_WhenRequested() {
        // given
        given(bookRepository.findTopPopularBooks(PageRequest.of(0, 2))).willReturn(Arrays.asList(book1, book2));

        // when
        List<Book> popularBooks = bookService.getTopPopularBooks(2);

        // then
        assertThat(popularBooks).hasSize(2).containsExactly(book1, book2);
        verify(bookRepository, times(1)).findTopPopularBooks(PageRequest.of(0, 2));
    }
}
