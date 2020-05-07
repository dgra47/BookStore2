package com.termos.controller;

import static org.mockito.Mockito.mock;

import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.termos.dto.ApiResponse;
import com.termos.dto.BookDTO;
import com.termos.service.BookService;

import org.junit.Assert;

@RunWith(MockitoJUnitRunner.class)
public class BookControlerTest {
    @Mock
    private BookService bookServiceMock;

    @Test
    public void test_Books_Get_if_Books_exists() {
        // given
        Mockito.when(bookServiceMock.findAll()).thenReturn(Stream.empty());
        Mockito.when(bookServiceMock.getRecordCount()).thenReturn(1);
        BookController bookControler = new BookController(bookServiceMock);

        // when
        ResponseEntity<ApiResponse<Stream<BookDTO>>> actual = bookControler.books();

        // then
        Assert.assertEquals("Message should be fixed", "List of all books, count: 1", actual.getBody().message);
        Assert.assertTrue("Message should be success if retrive books", actual.getBody().isSuccess);
    }

    @Test
    public void test_Books_Get_if_Books_does_not_exist() {
        // given
        Mockito.when(bookServiceMock.findAll()).thenReturn(Stream.empty());
        Mockito.when(bookServiceMock.getRecordCount()).thenReturn(0);
        BookController bookControler = new BookController(bookServiceMock);

        // when
        ResponseEntity<ApiResponse<Stream<BookDTO>>> actual = bookControler.books();

        // then
        Assert.assertEquals("Message should be fixed", "No content on store.", actual.getBody().message);
        Assert.assertTrue("Message should be success if retrive no books", actual.getBody().isSuccess);
    }

    @Test
    public void test_Books_Get_retering_the_same_stream_as_bookService_findAll_method() {
        // given
        Stream<BookDTO> aStream = Stream.empty();
        Mockito.when(bookServiceMock.findAll()).thenReturn(aStream);
        Mockito.when(bookServiceMock.getRecordCount()).thenReturn(1);
        BookController bookControler = new BookController(bookServiceMock);

        // when
        ResponseEntity<ApiResponse<Stream<BookDTO>>> actual = bookControler.books();

        // then
        Assert.assertTrue("Message should be fixed", aStream == actual.getBody().payload);
    }
}
