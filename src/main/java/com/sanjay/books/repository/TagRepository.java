package com.sanjay.books.repository;

import com.sanjay.books.model.Book;
import com.sanjay.books.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    @Query("SELECT b FROM Book b JOIN  Tag t  on t.bookId=b.id WHERE t.value = LOWER(:tag)")
    List<Book> retrieveByTag(@Param("tag") String tag);
}
