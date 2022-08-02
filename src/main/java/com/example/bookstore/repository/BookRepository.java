package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {

   @Query (value =  "select * from book where book_name=:book_name",nativeQuery = true)
    List<Book> findByBookName (@Param("book_name") String bookName);

   @Query (value =  "select * from book order by book_name asc ",nativeQuery = true)
   List<Book> findAll ();

}
