package com.termos.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


public class OrderDTO extends AbstractDTO{
    @NotNull
    @NotEmpty
    private String orderId;

    @NotNull
    @NotEmpty
    private String bookId;

    @NotNull
    @NotEmpty
    private String userId;


    @NotNull
    private java.sql.Timestamp orderDate;

    @NotNull
    private int quantity;

    @NotNull
    @NotEmpty
    private float price;

    @NotNull
    @NotEmpty
    private String status;

    @NotNull
    @NotEmpty
    private String invoice;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }
}