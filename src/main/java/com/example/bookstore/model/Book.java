package com.example.bookstore.model;

import com.example.bookstore.dto.BookDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@RequiredArgsConstructor
public class Book {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bookName;
    private String authorName;
    private String bookDescription;
    private String bookImg;
    private double price;
    private int qty;

    public Book(BookDTO bookDTO){
        this.bookName=bookDTO.bookName;
        this.authorName=bookDTO.authorName;
        this.bookDescription=bookDTO.bookDescription;
        this.bookImg=bookDTO.bookImg;
        this.price=bookDTO.price;
        this.qty=bookDTO.qty;
    }

    public Book(int id,BookDTO bookDTO){
        this.id = id;
        this.bookName=bookDTO.bookName;
        this.authorName=bookDTO.authorName;
        this.bookDescription=bookDTO.bookDescription;
        this.bookImg=bookDTO.bookImg;
        this.price=bookDTO.price;
        this.qty=bookDTO.qty;
    }

    public void updateBookData(BookDTO bookDTO) {
        this.bookName=bookDTO.bookName;
        this.authorName=bookDTO.authorName;
        this.bookDescription=bookDTO.bookDescription;
        this.bookImg=bookDTO.bookImg;
        this.price=bookDTO.price;
        this.qty=bookDTO.qty;
    }


}
