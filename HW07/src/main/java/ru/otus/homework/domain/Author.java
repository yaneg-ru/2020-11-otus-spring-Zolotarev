package ru.otus.homework.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "author")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Setter
@Getter
@NamedEntityGraph(name = "author-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "books", subgraph = "comment-graph")
        }
)
public class Author extends BaseNamedEntity {

    @OneToMany
    @JoinColumn(name = "author_id")
    List<Book> books;
}
