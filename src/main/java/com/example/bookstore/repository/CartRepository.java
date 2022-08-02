package com.example.bookstore.repository;

import com.example.bookstore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart , Integer> {
    @Query(value = "SELECT * FROM BookStore.cart where user_id = :user_id AND book_id = :book_id",nativeQuery = true)
    Cart findByUserId(int user_id,int book_id);

    @Query(value = "SELECT * FROM BookStore.cart where user_id =:id",nativeQuery = true)
    List<Cart> findByUser(int id);
}
