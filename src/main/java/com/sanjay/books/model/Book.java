package com.sanjay.books.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sanjay.books.dto.BookDto;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private @NotNull Integer id;

    @JsonIgnore
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }

    private @NotNull String isbn;

    String author;

    @JsonIgnore
    public Book() {
    }

    @JsonIgnore
    @OneToMany( fetch=FetchType.LAZY,mappedBy = "book")
    private Set<Tag> tags;

    private @NotNull String title;

    private @NotNull
    Date createdDate;

    @JsonIgnore
    public Book(BookDto bookDto) {
        this.author = bookDto.getAuthor();
        this.title = bookDto.getTitle();
        this.isbn = bookDto.getIsbn();
        this.tags = new HashSet<>(bookDto.getTags());
        this.createdDate = new Date();
    }

    @JsonIgnore
    public BookDto getDto(){
        return  new BookDto(id,isbn,author,title,new ArrayList<>(tags));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id) && isbn.equals(book.isbn) && Objects.equals(author, book.author)  && title.equals(book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, author, title, createdDate);
    }
}
