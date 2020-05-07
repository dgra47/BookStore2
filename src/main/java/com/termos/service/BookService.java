package com.termos.service;

import com.termos.dto.BookDTO;
import com.termos.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class BookService implements AbstractService<BookDTO> {
    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDTO persistNew(BookDTO book) {
        return bookRepository.modelToDto(bookRepository.persist(bookRepository.dtoToModel(book)));
    }

    @Override
    public String remove(String id) {
        return bookRepository.remove(id);
    }

    @Override
    public BookDTO getById(String id) {
        return bookRepository.modelToDto(bookRepository.getById(id));
    }

    public Stream<BookDTO> findAll(){
        return bookRepository.findAll().stream().map(book -> bookRepository.modelToDto(book));
    }

    @Override
    public int getRecordCount() {
        return bookRepository.count();
    }
}