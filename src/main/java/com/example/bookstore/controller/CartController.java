package com.example.bookstore.controller;

import com.example.bookstore.dto.CartDTO;
import com.example.bookstore.dto.ResponseDTO;
import com.example.bookstore.model.Cart;
import com.example.bookstore.service.iCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    iCartService service;
    List<Cart> cartDatalist = new ArrayList<>();

    @RequestMapping(value = {"","/", "/getCartAll"})
    public ResponseEntity<ResponseDTO> getCartData() {
        cartDatalist = service.getCartData();
        ResponseDTO respOTO = new ResponseDTO("Get Call Successful", cartDatalist);
        return new ResponseEntity<ResponseDTO>(respOTO, HttpStatus.OK);
    }
    @GetMapping("/getCartById/{cartId}")
    public ResponseEntity<ResponseDTO> getUserDataById(@PathVariable int cartId) {
        Cart cartData = service.getCartDataById(cartId);
        ResponseDTO respDTO= new ResponseDTO("Get Call For ID Successful", cartData);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }
    @PostMapping("/addCart/{token}")
    public ResponseEntity<ResponseDTO> createCartData(@PathVariable String token,@RequestBody CartDTO cartDTO) {
        Cart cartData = service.insertItems(cartDTO,token);
        ResponseDTO respOTO= new ResponseDTO("Created Cart  Data Successfully", cartData);
        return new ResponseEntity<ResponseDTO>(respOTO, HttpStatus.OK);
    }
    @PutMapping("/updateCart/{cartID}")
    public ResponseEntity<ResponseDTO> updateCartItems(@PathVariable Integer cartID, @Valid @RequestBody CartDTO cartdto) {
        Cart newCart = service.updateCartItems(cartID, cartdto);
        ResponseDTO responseDTO = new ResponseDTO("Record updated successfully !", newCart);
        return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteCart/{cartID}")
    public ResponseEntity<ResponseDTO> deleteCartItems(@PathVariable Integer cartID) {
        Cart newCart = service.deleteCartItems(cartID);
        ResponseDTO responseDTO = new ResponseDTO("Record deleted successfully !", newCart);
        return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateQuantity/{cartID}/{quantity}")
    public ResponseEntity<ResponseDTO> updateQuantityItems(@PathVariable Integer cartID, @PathVariable Integer quantity) {
        Cart newCart = service.updateQuantityItems(cartID, quantity);
        ResponseDTO responseDTO = new ResponseDTO("Quantity for book record updated successfully !", newCart);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }


}
