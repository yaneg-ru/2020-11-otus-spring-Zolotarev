package ru.otus.homework.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Setter
@Getter
@SuperBuilder
public class Book extends BaseNamedEntity {
    private Author author;
    private Genre genre;
}
