package com.example.bookstore.service;

import com.example.bookstore.dto.LoginDTO;
import com.example.bookstore.dto.UserDTO;
import com.example.bookstore.model.User;

import java.util.List;

public interface iUserService {

    List<User> getUserData();

    User  getUserDataById(String token);

    User createUserData(UserDTO userDTO);

    User updateUserData(String token, UserDTO userDTO);

    String deleteUserData(String token);

    User Search(LoginDTO loginDTO);

    String forgotPassword(LoginDTO loginDTO);

    User verifyUser(String token);
}
