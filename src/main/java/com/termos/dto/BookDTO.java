package com.termos.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class BookDTO extends AbstractDTO {
    @NotNull
    @NotEmpty
    public String bookId;

    @NotNull
    @NotEmpty
    public String title;

    @NotNull
    @NotEmpty
    public String author;

    @NotNull
    @NotEmpty
    public float price = 0.00f;

    @NotNull
    @NotEmpty
    public String description;

    @NotNull
    @NotEmpty
    public Date releaseDate;
}