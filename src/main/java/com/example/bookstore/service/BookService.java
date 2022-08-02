package com.example.bookstore.service;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.exception.BookStoreException;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements iBookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> getBookData() {
        return bookRepository.findAll();
    }
    @Override
    public Book getBookDataById(int bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookStoreException("Book With id " + bookId + " note Found "));
    }
    @Override
    public Book createBookData(BookDTO bookDTO){
        Book bookData = new Book(bookDTO);
        return bookRepository.save(bookData);
    }
    @Override
    public Book updateBookData(int bookId, BookDTO bookDTO){
        Book bookStoreDetails=this.getBookDataById(bookId);
        bookStoreDetails.updateBookData(bookDTO);
        return  bookRepository.save(bookStoreDetails);
    }
    @Override
    public String deleteBookData(int bookID) {
        Optional<Book> bookStoreDetails = bookRepository.findById(bookID);
        if (bookStoreDetails.isPresent()){
            bookRepository.deleteById(bookID);
            return "Data Deleted";
        }
        throw new BookStoreException("Book id " + bookID +" is not found");
    }
    @Override
    public List<Book> findByBookName(String bookName){
        List<Book> bookList = bookRepository.findByBookName(bookName);
        if (bookList.isEmpty()){
            throw new BookStoreException("Book Name is " + bookName + " is not found ");
        }
        return bookList;
    }

    @Override
    public List<Book> sortByBookName() {
        List<Book> bookList = bookRepository.findAll();
        return bookList;
    }

}
