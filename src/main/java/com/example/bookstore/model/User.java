package com.example.bookstore.model;

import com.example.bookstore.dto.UserDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String password;
    private String email;
    private String address;

    private String token;

    private boolean verified = false;

    public User(UserDTO userDTO){
        this.updateUser(userDTO);
    }

    public void updateUser(UserDTO userDTO) {
        this.userName=userDTO.userName;
        this.password=userDTO.password;
        this.email=userDTO.email;
        this.address=userDTO.address;
    }
}
