package com.sanjay.books;


import com.sanjay.books.controller.BookController;
import com.sanjay.books.dto.BookDto;
import com.sanjay.books.exception.FileInputException;
import com.sanjay.books.model.Book;
import com.sanjay.books.repository.BookRespository;
import com.sanjay.books.service.BookService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTest
{
    @InjectMocks
    BookController bookController;


    @Mock
    BookService bookService;



    @Test
    public void shouldReturnSearchResultForQuery() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        BookDto b1= new BookDto(1,"UIUI-2322-211-1","MYSELF","NEW",null);
        BookDto b2= new BookDto(2,"PIPI-2322-211-2","MYSELF","OLD",null);

        when(bookService.searchBooks("test")).thenReturn(Arrays.asList(b1,b2));

        ResponseEntity<List<BookDto>> responseEntity = bookController.searchBooks("test");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(2);
    }


    @Test
    public void shouldReturnEmptyListWhenNotFound() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));


        ResponseEntity<List<BookDto>> responseEntity = bookController.searchBooks("test");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(0);
    }


    @Test(expected = FileInputException.class)
    public void shouldReturnAppropriateResponseWhenInsertingBook() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
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


        Mockito.doThrow(new FileInputException("No reason")).when(bookService).insertBooksInDatabase(Mockito.any());
        ResponseEntity<String> responseEntity = bookController.insertBooks(result);

    }


}