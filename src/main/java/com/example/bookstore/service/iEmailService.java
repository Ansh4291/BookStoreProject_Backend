package com.example.bookstore.service;

import com.example.bookstore.dto.ResponseDTO;
import com.example.bookstore.model.Email;
import org.springframework.http.ResponseEntity;

public interface iEmailService {
    ResponseEntity<ResponseDTO> sendMail(Email email);

    String getLink(String token);
}
