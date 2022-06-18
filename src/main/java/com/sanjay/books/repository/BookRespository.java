package com.sanjay.books.repository;

import com.sanjay.books.dto.BookDto;
import com.sanjay.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRespository extends JpaRepository<Book,Long> {

    List<Book> findByIsbn(String isbn);

    List<Book> findByAuthor(String name);

    List<Book> findByTitle(String title);

    @Query("SELECT t FROM Book t WHERE t.isbn = UPPER(:query) OR t.author =  UPPER(:query) OR t.title = UPPER(:query) ")
    List<Book> searchBooks(String query);


}
