package com.termos.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class Book extends AbstractModel {
    @JsonProperty("book_id")
    private String bookId;
    private String title;
    private String author;
    private float price;
    private String description;
    @JsonProperty("release_date")
    private Date releaseDate;

    public Book(String bookId, String title, String author, float price, String description, Date releaseDate) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    @Override
    public String getId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }


}