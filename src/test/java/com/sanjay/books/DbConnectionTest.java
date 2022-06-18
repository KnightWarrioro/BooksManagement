package com.sanjay.books;

import com.sanjay.books.model.Book;
import com.sanjay.books.model.Tag;
import com.sanjay.books.repository.BookRespository;
import com.sanjay.books.repository.TagRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DbConnectionTest {

    @Autowired
    TagRepository tagRepository;
    @Autowired
    BookRespository bookRespository;

    @After
    public void tearDown(){
        tagRepository.deleteAll();
        bookRespository.deleteAll();
    }
    @Test
    public void emptyTagDatabaseShouldReturnEmptyArray() {
        List<Tag> tag = tagRepository.findAll();
        List<Tag> expectedResponse = new ArrayList<>();
        Assert.assertEquals(tag,expectedResponse);
    }

    @Test
    public void emptyBookDatabaseShouldReturnEmptyArray() {
        bookRespository.deleteAll();
        List<Book> books = bookRespository.findAll();
        List<Book> expectedResponse = new ArrayList<>();
        Assert.assertEquals(expectedResponse,books);
    }

    @Test
    public void searchBookShouldReturnResult() {
        Book b1= new Book();
        b1.setTitle("NEW");
        b1.setIsbn("UIUI-2322-211-1");
        b1.setAuthor("MYSELF");
        bookRespository.save(b1);
        List<Book> books = bookRespository.searchBooks("NEW");

        Assert.assertFalse(books.isEmpty());
        Assert.assertEquals(books.get(0),b1);
    }

    @Test
    public void searchBookForGivenTagShouldReturnResult() {
        List<Book> input = new ArrayList<>();
        Book b1= new Book();
        b1.setTitle("NEW");
        b1.setIsbn("UIUI-2322-211-1");
        b1.setAuthor("MYSELF");
        Book b2 = bookRespository.save(b1);

        Tag t1 = new Tag("tagged",b2.getId());
        tagRepository.save(t1);
        List<Book> books = tagRepository.retrieveByTag("TAGGED");

        Assert.assertFalse(books.isEmpty());
        Assert.assertEquals(books.get(0),b1);
    }

}