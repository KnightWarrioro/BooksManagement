package com.sanjay.books.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Objects;

@Entity
@Table(name="tags")
@Data
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Tag() {
    }

    private Integer bookId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) && Objects.equals(bookId, tag.bookId) && Objects.equals(value, tag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, value);
    }

    @JsonIgnore
    @ManyToOne(optional = false )
    @JoinColumn(name = "bookId",insertable = false,updatable = false)
    Book book;

    public Tag(String value,Integer book_id) {
        this.value = value;
        this.bookId = book_id;
    }

    private @NotNull
    String value;
}
