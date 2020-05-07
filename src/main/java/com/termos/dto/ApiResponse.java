package com.termos.dto;

public class ApiResponse<T> {
    public String message;
    public boolean isSuccess;
    public T payload;
}