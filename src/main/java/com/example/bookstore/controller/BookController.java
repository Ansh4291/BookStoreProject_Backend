package com.example.bookstore.controller;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.dto.ResponseDTO;
import com.example.bookstore.model.Book;

import com.example.bookstore.service.iBookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
@RestController
public class BookController {
    @Autowired
    iBookService service;
    List<Book> bookDatalist = new ArrayList<>();

    @RequestMapping(value = {"","/", "/getAll"})
    public ResponseEntity<ResponseDTO> getBookData() {
        bookDatalist = service.getBookData();
        ResponseDTO respOTO = new ResponseDTO("Get Call Successful", bookDatalist);
        return new ResponseEntity<ResponseDTO>(respOTO, HttpStatus.OK);
    }

    @GetMapping("/getBookById/{bookId}")
    public ResponseEntity<ResponseDTO> getUserDataById(@PathVariable int bookId) {
        Book bookData = service.getBookDataById(bookId);
        ResponseDTO respDTO= new ResponseDTO("Get Call For ID Successful", bookData);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

   @PostMapping("/addBook")
    public ResponseEntity<ResponseDTO> createBookData(
            @Valid @RequestBody BookDTO bookDTO) {
        Book bookData = service.createBookData(bookDTO);
        ResponseDTO respOTO= new ResponseDTO("Created Book  Data Successfully", bookData);
        return new ResponseEntity<ResponseDTO>(respOTO, HttpStatus.OK);
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<ResponseDTO> updateBookData(@PathVariable int id,@Valid @RequestBody BookDTO bookDTO) {
        ResponseDTO respDTO= new ResponseDTO("Updated User Details Successfully",service.updateBookData(id,bookDTO));
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook/{bookId}")
    public ResponseEntity <ResponseDTO> deleteBookData(@PathVariable int bookId) {
        service.deleteBookData(bookId);
        ResponseDTO respDTO= new ResponseDTO("Deleted Successfully", "Deleted id: "+bookId);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }
    @GetMapping("/bookName/{bookName}")
    public ResponseEntity<ResponseDTO> findByBookName(@PathVariable("bookName") String bookName) {
        List<Book> bookDatalist = service.findByBookName(bookName);
        ResponseDTO respOTO= new ResponseDTO("Get Call For ID Successful", bookDatalist);
        return new ResponseEntity<ResponseDTO>(respOTO, HttpStatus.OK);
    }

    @GetMapping("/sortByBook")
    public ResponseEntity<ResponseDTO> sortByBookName(){
        ResponseDTO responseDTO=new ResponseDTO("Sort By Book Name",service.sortByBookName());
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }

}
