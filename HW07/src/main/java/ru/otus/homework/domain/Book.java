package ru.otus.homework.domain;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "book")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
@NamedEntityGraph(name = "book-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("author"),
                @NamedAttributeNode("genre"),
                @NamedAttributeNode(value = "comments")
        }
)
public class Book extends BaseNamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    private Genre genre;

    @Fetch(value = FetchMode.SELECT)
    @BatchSize(size = 5)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private List<Comment> comments;

    @Override
    public String toString() {
        return "Book{" +
                "Id=" + super.getId() +
                " name=" + super.getName() +
                " authorId=" + author.getId() +
                ", genreId=" + genre.getId() +
                ", comments=" + comments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Book book = (Book) o;
        return Objects.equals(author.getId(), book.author.getId()) &&
                Objects.equals(genre.getId(), book.genre.getId()) &&
                Objects.equals(comments, book.comments);
    }

}
