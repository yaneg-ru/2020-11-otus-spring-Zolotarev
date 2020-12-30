package ru.otus.homework.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.otus.homework.dao.jpa.BookDaoJpa;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.service.CrudServiceImpl;

@SuppressWarnings("unused")
@Service
@ShellComponent
@RequiredArgsConstructor
public class BookServiceImpl extends CrudServiceImpl<Book, BookDaoJpa> {

    public static final String TRY_AGAIN = "Try again!";
    public static final String BOOK_DELETED = "Book deleted";
    public static final String NOTHING_FOUND = "Nothing found";

    private final AuthorServiceImpl authorService;
    private final GenreServiceImpl genreService;

    @ShellMethod(value = "Count books", key = {"cb"})
    @Override
    @Transactional(readOnly = true)
    public long count() {
        return super.count();
    }

    @ShellMethod(value = "Add book", key = {"ab"})
    @Transactional
    public String createBook(@ShellOption String name, @ShellOption Long authorId,
                         @ShellOption Long genreId) {
        String result;
        try {
            Author author = authorService.getById(authorId);
            Genre genre = genreService.getById(genreId);
            Book newBook = Book.builder()
                    .name(name)
                    .genre(genre)
                    .author(author)
                    .build();
            Book createdBook = super.create(newBook);
            result = createdBook.toString();
        }
        catch (Exception e) {
            return e.getMessage() + System.lineSeparator() + TRY_AGAIN;
        }
        return result;
    }

    @ShellMethod(value = "Update book", key = {"ub"})
    @Transactional
    public String updateBook(@ShellOption Long bookId, @ShellOption String name,
                         @ShellOption Long authorId, @ShellOption Long genreId) {
        String result;
        try {
            Author author = authorService.getById(authorId);
            Genre genre = genreService.getById(genreId);
            Book bookToUpdate = Book.builder()
                    .id(bookId)
                    .name(name)
                    .genre(genre)
                    .author(author)
                    .build();
            Book updatedBook = super.update(bookToUpdate);
            result = updatedBook.toString();
        }
        catch (Exception e) {
            return e.getMessage() + System.lineSeparator() + TRY_AGAIN;
        }
        return result;
    }

    @ShellMethod(value = "Get book by id", key = {"gbi"})
    @Transactional(readOnly = true)
    public String getBookById(@ShellOption Long id) {
        Book foundBook = super.getById(id);
        if (foundBook != null) {
            return foundBook.toString();
        } else {
            return NOTHING_FOUND;
        }
    }

    @ShellMethod(value = "Get all books", key = {"gab"})
    @Transactional(readOnly = true)
    public String getAllBooks() {
        List<Book> foundBooks = super.getAll();
        if (!foundBooks.isEmpty()) {
            return foundBooks.stream()
                    .map(Book::toString)
                    .collect(Collectors.joining(System.lineSeparator()));
        } else {
            return NOTHING_FOUND;
        }
    }

    @ShellMethod(value = "Get all books by genreId", key = {"gabg"})
    @Transactional(readOnly = true)
    public String getAllByGenre(@ShellOption Long genreId) {
        List<Book> foundBooks = super.repository.getAll();
        if (!foundBooks.isEmpty()) {
            return foundBooks.stream()
                    .filter(book -> book.getGenre().getId().equals(genreId))
                    .map(Book::toString)
                    .collect(Collectors.joining(System.lineSeparator()));
        } else {
            return NOTHING_FOUND;
        }
    }

    @ShellMethod(value = "Get all books by authorId", key = {"gaba"})
    @Transactional(readOnly = true)
    public String getAllByAuthor(@ShellOption Long authorId) {
        List<Book> foundBooks = super.repository.getAll();
        if (!foundBooks.isEmpty()) {
            return foundBooks.stream()
                    .filter(book -> book.getAuthor().getId().equals(authorId))
                    .map(Book::toString)
                    .collect(Collectors.joining(System.lineSeparator()));
        } else {
            return NOTHING_FOUND;
        }
    }

    @ShellMethod(value = "Delete book by id", key = {"db"})
    @Transactional(readOnly = true)
    public String deleteBookById(@ShellOption Long id) {
        try {
            super.deleteById(id);
            return BOOK_DELETED;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
