package com.example.bookstore.repository;

import com.example.bookstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface OrderRepository extends JpaRepository<Order , Integer>{

    @Query(value = " SELECT * FROM  BookStore.order_details WHERE user_id = :user_id",nativeQuery = true)
    Order findByUserId(int user_id);

    @Modifying
    @Transactional
    @Query(value = "update BookStore.order_details set cancel=true where order_id=:id", nativeQuery = true)
    int updateCancel(int id);


}
