package com.sanjay.books.controller;

import com.sanjay.books.dto.BookDto;
import com.sanjay.books.exception.FileInputException;
import com.sanjay.books.repository.BookRespository;
import com.sanjay.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/upload/books")
    public ResponseEntity<String> insertBooks(@RequestParam(value = "file", required = true) MultipartFile file) throws FileInputException {
       bookService.insertBooksInDatabase(file) ;
       return new ResponseEntity<>("Started File upload ", HttpStatus.ACCEPTED);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<BookDto>> searchBooks(@PathVariable(value = "query") String query)  {
       List<BookDto> books =  bookService.searchBooks(query);
       return  ResponseEntity.ok(books);
    }

    @GetMapping("/book")
    private List<BookDto> getAllBooks()
    {
        return bookService.getAllBooks();
    }
    //creating a get mapping that retrieves the detail of a specific book
    @GetMapping("/book/{bookid}")
    private BookDto getBooks(@PathVariable("bookid") Long bookid)
    {
        return bookService.getBooksById(bookid);
    }
    //creating a delete mapping that deletes a specified book
    @DeleteMapping("/book/{bookid}")
    private void deleteBook(@PathVariable("bookid") Long bookid)
    {
        bookService.delete(bookid);
    }
    //creating post mapping that post the book detail in the database
    @PostMapping("/books")
    private BookDto saveBook(@RequestBody BookDto books)
    {
        BookDto bookDto = bookService.saveOrUpdate(books);
        return bookDto;
    }
    //creating put mapping that updates the book detail
    @PutMapping("/books")
    private BookDto update(@RequestBody BookDto books)
    {
        BookDto bookDto = bookService.saveOrUpdate(books);
        return bookDto;
    }
}