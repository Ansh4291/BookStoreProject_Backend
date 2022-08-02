package com.example.bookstore.controller;

import com.example.bookstore.dto.LoginDTO;
import com.example.bookstore.dto.ResponseDTO;
import com.example.bookstore.dto.UserDTO;
import com.example.bookstore.model.User;
import com.example.bookstore.service.iUserService;
import com.example.bookstore.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    @Autowired
    iUserService service;
    List<User> userDatalist = new ArrayList<>();

    @RequestMapping(value = {"","/", "/get"})
    public ResponseEntity<ResponseDTO> getUserData() {
        userDatalist = service.getUserData();
        ResponseDTO respOTO = new ResponseDTO("Get Call Successful", userDatalist);
        return new ResponseEntity<ResponseDTO>(respOTO, HttpStatus.OK);
    }

    @GetMapping("/get/{token}")
    public ResponseEntity<ResponseDTO> getUserData(@PathVariable  String token) {
        User userData= null;
        userData = service.getUserDataById(token);
        ResponseDTO respDTO= new ResponseDTO("Get Call For ID Successful", userData);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> createUserData(
           @Valid @RequestBody UserDTO empDTO) {
        User userData = null;
        userData = service.createUserData(empDTO);
        ResponseDTO respOTO= new ResponseDTO("Created User  Data Successfully", userData);
        return new ResponseEntity<ResponseDTO>(respOTO, HttpStatus.OK);
    }

    @PutMapping("/update/{token}")
    public ResponseEntity<ResponseDTO> updateUserData(@PathVariable String token,@Valid @RequestBody UserDTO userDTO) {
        ResponseDTO respDTO= new ResponseDTO("Updated User Details Successfully",service.updateUserData(token,userDTO));
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{token}")
    public ResponseEntity <ResponseDTO> deleteUserData(@PathVariable String token) {
        service.deleteUserData(token);
        ResponseDTO respDTO= new ResponseDTO("Deleted Successfully", "Deleted id: "+token);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }
    @PostMapping("/getEmailPass")
    public ResponseEntity<ResponseDTO> getEmailPass(@RequestBody LoginDTO loginDTO)
    {
        ResponseDTO respDTO= new ResponseDTO("Get Call For Login Successful", service.Search(loginDTO));
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }
    @PostMapping("/forgotPass")
    public ResponseEntity<ResponseDTO> loginCheck(@RequestBody LoginDTO loginDTO){
        ResponseDTO responseDTO = new ResponseDTO("UserName is valid" ,service.forgotPassword(loginDTO) );
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
    @GetMapping("/verify/{token}")
    public ResponseEntity<ResponseDTO> verifyUser(@PathVariable String token){
        ResponseDTO responseDTO = new ResponseDTO("User verified successfully", service.verifyUser(token));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
