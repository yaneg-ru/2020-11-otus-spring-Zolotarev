package ru.otus.homework.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.homework.dao.jdbc.AuthorDaoJdbc;
import ru.otus.homework.dao.jdbc.BookDaoJdbc;
import ru.otus.homework.dao.jdbc.GenreDaoJdbc;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

@ExtendWith(SpringExtension.class)
@JdbcTest
@Import({BookDaoJdbc.class, GenreDaoJdbc.class, AuthorDaoJdbc.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@TestInstance(PER_CLASS)
class BookDaoJdbcTest {

    public static final String NEW_BOOK = "Сборник стихов";
    public static final Long EXISTING_BOOK_ID = 1L;
    public static final Long NEW_BOOK_ID = 3L;
    public static final Long EXISTING_AUTHOR_ID = 1L;
    public static final Long EXISTING_GENRE_ID = 1L;
    public static final String PUSHKIN = "Пушкин А.С.";
    public static final String POESIA = "Поэзия";

    private Book book;

    @Autowired
    private BookDaoJdbc repositoryBook;


    @BeforeAll
    void setupBeforeAll() {
        Author author = Author.builder()
                .id(EXISTING_AUTHOR_ID)
                .name(PUSHKIN)
                .build();
        Genre genre = Genre.builder()
                .id(EXISTING_GENRE_ID)
                .name(POESIA)
                .build();
        book = Book.builder()
                .id(NEW_BOOK_ID)
                .name(NEW_BOOK)
                .author(author)
                .genre(genre)
                .build();
    }

    @Test
    void count_shouldGetTwo_whenThereAreTwoBooksInDatabase() {
        int actualCount = repositoryBook.count();
        assertEquals(2, actualCount);
    }

    @Test
    void insert_shouldInsertNewBook_whenPassedValidData() {
        repositoryBook.insert(book);
        Book actualBook = repositoryBook.getById(book.getId());
        assertEquals(book, actualBook);
    }

    @Test
    void getById_shouldGetOneBook_whenThereAreOneBookInDatabaseByPassedId() {
        Book actualBook = repositoryBook.getById(EXISTING_BOOK_ID);
        assertNotNull(actualBook);
    }

    @Test
    void getAll_shouldGetTwoBooks_whenThereAreTwoBooksInDatabase() {
        List<Book> actualBooks = repositoryBook.getAll();
        assertEquals(2, actualBooks.size());
    }

    @Test
    void getAllByAuthor_shouldGetOneBook_whenThereIsOneBookInDatabaseByPassedAuthorId() {
        List<Book> actualBooks = repositoryBook.getAllByAuthor(EXISTING_AUTHOR_ID);
        assertEquals(1, actualBooks.size());
    }

    @Test
    void getAllByGenre_shouldGetOneBook_whenThereIsOneBookInDatabaseByPassedGenreId() {
        List<Book> actualBooks = repositoryBook.getAllByGenre(EXISTING_GENRE_ID);
        assertEquals(1, actualBooks.size());
    }

    @Test
    void deleteById_shouldDeleteBook_whenPassedValidBookId() {
        repositoryBook.deleteById(EXISTING_BOOK_ID);
        assertNull(repositoryBook.getById(EXISTING_BOOK_ID));
    }

}