package com.termos.repository;

import com.termos.dto.AbstractDTO;
import com.termos.dto.BookDTO;
import com.termos.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class BookRepository implements AbstractRepository<Book> {
    private DataSource dataSource;

    @Autowired
    public BookRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            ResultSet rs = connection.prepareStatement("select * from books").executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Book getById(String id) {
        Book book = null;
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select * from books where book_id=?";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            book = map(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public Book persist(Book book) {
        Connection connection;
        try {
            connection = dataSource.getConnection();

            String sql = "INSERT INTO books(book_id, title, author,  price, description, release_date) VALUES(?,?,?,?,?,?);";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setDouble(4, book.getPrice());
            preparedStatement.setString(5, book.getDescription());
            preparedStatement.setDate(6, book.getReleaseDate());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            book = null;
        }
        return book;
    }

    @Override
    public String remove(String id) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "DELETE from books where book_id=?";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public Book map(ResultSet rslt) throws SQLException {
        return new Book(rslt.getString("book_id"),
                rslt.getString("title"),
                rslt.getString("author"),
                rslt.getInt("price"),
                rslt.getString("description"),
                rslt.getDate("release_date"));
    }

    @Override
    public Book dtoToModel(AbstractDTO dto) {
        BookDTO bookDTO = (BookDTO) dto;
        return new Book(bookDTO.bookId,
                bookDTO.title,
                bookDTO.author,
                bookDTO.price,
                bookDTO.description,
                bookDTO.releaseDate
        );
    }

    @Override
    public BookDTO modelToDto(Book book) {
        var bookDto = new BookDTO();
        if (book == null) {
            return null;
        }
        bookDto.author = book.getAuthor();
        bookDto.bookId = book.getId();
        bookDto.description = book.getDescription();
        bookDto.price = book.getPrice();
        bookDto.title = book.getTitle();
        bookDto.releaseDate = book.getReleaseDate();
        return bookDto;
    }

    @Override
    public int count() {
        try {
            ResultSet rs = dataSource.getConnection().prepareStatement("SELECT COUNT(book_id) FROM books").executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
