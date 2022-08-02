package com.example.bookstore.service;

import com.example.bookstore.dto.CartDTO;
import com.example.bookstore.exception.BookStoreException;
import com.example.bookstore.model.Cart;

import java.util.List;

public interface iCartService {

    List<Cart> getCartData();

    Cart getCartDataById(int cartId);

    Cart insertItems(CartDTO cartDTO, String token) throws BookStoreException;

    Cart updateCartItems(Integer cartID, CartDTO cartdto);

    Cart deleteCartItems(Integer cartID);

    Cart updateQuantityItems(Integer cartID, Integer quantity);
}
