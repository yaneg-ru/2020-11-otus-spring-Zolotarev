package ru.otus.homework.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.otus.homework.service.impl.BookServiceImpl.BOOK_DELETED;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ru.otus.homework.dao.jpa.BookDaoJpa;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

@SpringBootTest
@TestInstance(PER_CLASS)
class BookServiceImplTest {

    public static final String NAME_GENRE = "Name genre";
    public static final long GENRE_ID = 1L;
    public static final long AUTHOR_ID = 2L;
    public static final String AUTHOR_NAME = "Author name";
    public static final long BOOK_ID = 4L;

    @Autowired
    BookServiceImpl service;

    @MockBean
    private AuthorServiceImpl authorService;

    @MockBean
    private GenreServiceImpl genreService;

    @MockBean
    private BookDaoJpa bookRepository;

    private Author author;
    private Genre genre;
    private Book book;

    @BeforeAll
    void setupBeforeAll() {
        genre = Genre.builder()
                .name(NAME_GENRE)
                .id(GENRE_ID)
                .build();
        author = Author.builder()
                .name(AUTHOR_NAME)
                .id(AUTHOR_ID)
                .build();
        book = Book.builder()
                .id(BOOK_ID)
                .name("Book name")
                .author(author)
                .genre(genre)
                .build();
    }

    @Test
    void count_shouldGetTwo_whenAccordingWithPlannedBehavior() {
        when(bookRepository.count()).thenReturn(2L);
        long actualCount = service.count();
        assertEquals(2, actualCount);
        verify(bookRepository, times(1)).count();
    }

    @Test
    void createBook_shouldAddNewBook_whenPassedValidData() {
        when(authorService.getById(any())).thenReturn(author);
        when(genreService.getById(any())).thenReturn(genre);
        when(bookRepository.getById(any())).thenReturn(Optional.of(book));
        var expectedBook = book.toString();
        var actualBook = service.createBook(book.getName(), book.getAuthor().getId(),
                                               book.getGenre().getId()
        );
        assertEquals(expectedBook, actualBook);
        verify(authorService, times(1)).getById(any());
        verify(genreService, times(1)).getById(any());
        verify(bookRepository, times(1)).getById(any());
    }

    @Test
    void updateBook_shouldUpdateBook_whenPassedValidData() {
        when(authorService.getById(any())).thenReturn(author);
        when(genreService.getById(any())).thenReturn(genre);
        when(bookRepository.getById(any())).thenReturn(Optional.of(book));
        var expectedBook = book.toString();
        var actualBook = service.updateBook(book.getId(), book.getName(),
                                               book.getAuthor().getId(), book.getGenre().getId()
        );
        assertEquals(expectedBook, actualBook);
        verify(authorService, times(1)).getById(any());
        verify(genreService, times(1)).getById(any());
        verify(bookRepository, times(1)).getById(any());
    }

    @Test
    void getBookById_shouldGetBook_whenPassedValidData() {
        when(bookRepository.getById(any())).thenReturn(Optional.of(book));
        var expectedBook = book.toString();
        var actualBook = service.getBookById(book.getId());
        assertEquals(expectedBook, actualBook);
        verify(bookRepository, times(1)).getById(any());
    }

    @Test
    void getAllBooks_shouldOneBook_whenAccordingWithPlannedBehavior() {
        when(bookRepository.getAll()).thenReturn(List.of(book));
        var expectedListBook = List.of(book);
        var actualListBook = service.getAll();
        assertEquals(expectedListBook, actualListBook);
        verify(bookRepository, times(1)).getAll();
    }

    @Test
    void getAllByGenre_shouldOneBook_whenAccordingWithPlannedBehavior() {
        when(bookRepository.getAll()).thenReturn(List.of(book));
        var expectedListBook = book.toString();
        var actualListBook = service.getAllByGenre(genre.getId());
        assertEquals(expectedListBook, actualListBook);
        verify(bookRepository, times(1)).getAll();
    }

    @Test
    void getAllByAuthor_shouldOneBook_whenAccordingWithPlannedBehavior() {
        when(bookRepository.getAll()).thenReturn(List.of(book));
        var expectedListBook = book.toString();
        var actualListBook = service.getAllByAuthor(author.getId());
        assertEquals(expectedListBook, actualListBook);
        verify(bookRepository, times(1)).getAll();
    }

    @Test
    void deleteBookById_shouldDeleteBook_whenAccordingWithPlannedBehavior() {
        var actualResult = service.deleteBookById(book.getId());
        assertEquals(BOOK_DELETED, actualResult);
    }


}