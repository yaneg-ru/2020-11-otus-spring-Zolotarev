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
@Table(name = "genre")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Setter
@Getter
@SuperBuilder
@NamedEntityGraph(name = "genre-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "books", subgraph = "comment-graph")
        }
)
public class Genre extends BaseNamedEntity {
    @OneToMany
    @JoinColumn(name = "genre_id")
    private List<Book> books;

}
