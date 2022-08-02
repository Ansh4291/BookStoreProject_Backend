package com.example.bookstore.repository;

import com.example.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRegistrationRepository extends JpaRepository<User, Integer> {
    @Query(value = "select * from user where email=:email AND password =:password",nativeQuery = true)
    User find(String email,String password);

    @Query(value = "select * from BookStore.user where email=:email",nativeQuery = true)
    User forgotPassword(String email);
}
