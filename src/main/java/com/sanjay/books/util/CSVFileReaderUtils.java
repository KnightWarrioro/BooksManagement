package com.sanjay.books.util;

import com.sanjay.books.dto.BookDto;
import com.sanjay.books.model.Tag;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class CSVFileReaderUtils {
    static final String TAGS_DELIMITER = ",";

    public List<BookDto> getFileInput(MultipartFile file) throws IOException {
        Iterable<CSVRecord> csvRecords = null;

        BufferedReader fileReader = new BufferedReader(new
                InputStreamReader(file.getInputStream(), "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
        csvRecords = csvParser.getRecords();
        List<BookDto> books = new ArrayList<>();
        for (CSVRecord csvRecord : csvRecords) {
            List<Tag> tagList = new ArrayList<>();
            if (csvRecord.size() >= 4) {
                tagList.addAll(Arrays.stream(csvRecord.get(3).toUpperCase().split(TAGS_DELIMITER)).map(x -> new Tag(x.toUpperCase(), 1)).toList());
            }
            if(csvRecord.size()<3)
                throw new IOException("Incorrect Params");
            BookDto book = BookDto.builder()
                    .author(csvRecord.get(0).toUpperCase())
                    .title(csvRecord.get(1).toUpperCase())
                    .isbn(csvRecord.get(2).toUpperCase())
                    .tags(tagList)
                    .build();
            books.add(book);


        }
        return books;

    }
}
