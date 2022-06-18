package com.sanjay.books;

import com.sanjay.books.exception.FileInputException;
import com.sanjay.books.model.Book;
import com.sanjay.books.repository.BookRespository;
import com.sanjay.books.service.BookService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class BookServiceTest {

    @Autowired
    BookService bookService;

    @Autowired
    BookRespository bookRespository;

    @Test
    public void bookUploadShouldReturnSuccessResponseIfDataIsCorrect() throws FileInputException {
        Path path = Paths.get("src/test/resources/SeedBooks.csv");
        String name = "file.txt";
        String originalFileName = "file.txt";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);


        bookService.insertBooksInDatabase(result);


        List<Book> bookList = bookRespository.findAll();
        System.out.println("Here");
        Assert.assertEquals(bookList.size(),4);
    }


    @Test (expected = FileInputException.class)
    public void bookUploadShouldThrowExceptionIfDataIsInCorrect() throws FileInputException {
        Path path = Paths.get("src/test/resources/SeedFileError.csv");
        String name = "file.txt";
        String originalFileName = "file.txt";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);


        bookService.insertBooksInDatabase(result);

    }
}
