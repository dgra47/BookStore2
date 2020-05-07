package com.termos.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class Order extends AbstractModel{
    @JsonProperty("order_id")
    private String orderID;
    @JsonProperty("book_id")
    private String bookId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("order_date")
    private Timestamp orderDate;
    private int quantity;
    private float  price;
    private String status;
    private String invoice;

    public Order(String orderID, String bookId, String userId, Timestamp orderDate, int quantity, float price, String status, String invoice) {
        this.orderID = orderID;
        this.bookId = bookId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.invoice = invoice;
    }

    @Override
    public String getId() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
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

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", bookId='" + bookId + '\'' +
                ", userId='" + userId + '\'' +
                ", orderDate=" + orderDate +
                ", quantity=" + quantity +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", invoice='" + invoice + '\'' +
                '}';
    }
}