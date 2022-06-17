package com.sanjay.books.dto;

import com.sanjay.books.model.Book;
import com.sanjay.books.model.Tag;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
@Data
@Builder
public class BookDto {
    private Integer id;
    private @NotNull String isbn;
    private @NotNull String author;
    private @NotNull String title;
    private List<Tag> tags;


    public BookDto(Integer id, String isbn, String author, String title, List<Tag> tags) {
        this.id = id;
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.tags = tags;
    }

    public Book getBook(){

         Book  book = new Book();
         book.setId(id);
         book.setIsbn(isbn);
         book.setAuthor(author);
         book.setTitle(title);
         book.setTags(new HashSet<>(tags));
         return book;
    }

}
