package com.termos.controller;

import com.termos.dto.ApiResponse;
import com.termos.dto.BookDTO;
import com.termos.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.Stream;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    ResponseEntity<ApiResponse<Stream<BookDTO>>> books()  {
        var response = new ApiResponse<Stream<BookDTO>>();
        var stream = bookService.findAll();
        var count = bookService.getRecordCount();
        if(count == 0){
            response.message = "No content on store.";
            response.isSuccess = true;
            return ResponseEntity.ok(response);
        }

        response.isSuccess = true;
        response.message = "List of all books, count: "+count;
        response.payload = stream;
        return ResponseEntity.ok(response);
    }
    @GetMapping("/books/{id}")
    ResponseEntity<ApiResponse<BookDTO>> get(@PathVariable String id) {
        var response = new ApiResponse<BookDTO>();
        var bookDto = bookService.getById(id);
        response.payload = bookDto;
        response.message = bookDto != null ? "Book returned successfully." : "Couldn't get book data of id: "+id;
        response.isSuccess = bookDto != null;
        return ResponseEntity.ok(response);
    }

    @PostMapping("/books")
    public ResponseEntity create(@RequestBody BookDTO book) {
        var bookDto = bookService.persistNew(book);
        if(bookDto == null){
            return ResponseEntity.badRequest().body(book);
        }else{
            return ResponseEntity.created(URI.create("/books/"+bookDto.bookId)).body(bookDto);
        }
    }
    @PutMapping("/books")
    ResponseEntity<ApiResponse>  update(@RequestBody BookDTO bookDTO) {
        var response = new ApiResponse();
        response.message = "Book updated.";
        response.isSuccess = true;
        response.payload =  bookService.persistNew(bookDTO);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/books/{id}")
    ResponseEntity<ApiResponse>  deleteBook(@PathVariable String id) {
        var response = new ApiResponse();
        response.isSuccess = true;
        response.message = "Deleted book.";
        response.payload = bookService.remove(id);
        return ResponseEntity.ok(response);
    }
}