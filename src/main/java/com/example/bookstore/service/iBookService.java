package com.example.bookstore.service;


import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.dto.UserDTO;
import com.example.bookstore.model.Book;


import java.util.List;

public interface iBookService {


    List<Book> getBookData();

    Book getBookDataById(int bookId);

    Book createBookData(BookDTO bookDTO);


    Book updateBookData(int bookId, BookDTO bookDTO);

    String deleteBookData(int bookID);


    List<Book> findByBookName(String bookName);

    Object sortByBookName();
}
