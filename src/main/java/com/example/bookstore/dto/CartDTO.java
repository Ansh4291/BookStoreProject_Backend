package com.example.bookstore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private int userId;
    private int bookId;
    private int quantity;
}
