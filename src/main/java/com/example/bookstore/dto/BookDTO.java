package com.example.bookstore.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BookDTO {

    public String bookName;
    public String authorName;
    public String bookDescription;
    public String bookImg;
    public double price;
    public int qty;
}
