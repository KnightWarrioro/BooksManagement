package com.sanjay.books.service;

import com.sanjay.books.dto.BookDto;
import com.sanjay.books.exception.FileInputException;
import com.sanjay.books.model.Book;
import com.sanjay.books.model.Tag;
import com.sanjay.books.repository.BookRespository;
import com.sanjay.books.repository.TagRepository;
import com.sanjay.books.util.CSVFileReaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {



    @Autowired
    BookRespository bookRespository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    CSVFileReaderUtils csvFileReaderUtils;

    public void insertBooksInDatabase(MultipartFile file) throws FileInputException {
        try {
            List<BookDto> bookDtos = csvFileReaderUtils.getFileInput(file);
            List<Tag> tagList  = new ArrayList<>();
            for(BookDto book : bookDtos){
                Book savedBook = bookRespository.save(new Book(book));

                tagList.addAll(book.getTags().stream().map(x -> new Tag(x.getValue(),savedBook.getId())).toList());

            }
            tagRepository.saveAll(tagList);
        } catch (IOException exception) {
           throw  new FileInputException("Invalid input detected");
        }
    }

    public List<BookDto> searchBooks(String query) {
        query = query.toUpperCase();
        List<Book> searchResultByIsbn =  bookRespository.findByIsbn(query);
        List<Book> searchResultByTitle = bookRespository.findByTitle(query);
        List<Book> searchResultByAuthor = bookRespository.findByAuthor(query);
        List<Book> searchResultByTag = tagRepository.retrieveByTag(query);

        List<Book> searchResults = new ArrayList<>();

        searchResults.addAll(searchResultByAuthor);
        searchResults.addAll(searchResultByIsbn);
        searchResults.addAll(searchResultByTitle);
        searchResults.addAll(searchResultByTag);

        List<BookDto>  response = searchResults.stream().map(x -> x.getDto()).toList();
        return response;
    }

    public List<BookDto> getAllBooks() {
        List<Book> books = bookRespository.findAll();
        return books.stream().map(x-> x.getDto()).toList();
    }

    public BookDto getBooksById(Long bookid) {
        Optional<Book> book = bookRespository.findById(bookid);
        if(book.isPresent())
            return book.get().getDto();
        throw new NoResultException("No Book found for id" + bookid);
    }

    public void delete(Long bookid) {
         bookRespository.deleteById(bookid);
    }

    public BookDto saveOrUpdate(BookDto book) {
        Book bookToModify = book.getBook();
        bookRespository.save(bookToModify);
        return bookToModify.getDto();
    }
}
