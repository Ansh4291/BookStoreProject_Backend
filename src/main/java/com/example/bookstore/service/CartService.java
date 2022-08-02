package com.example.bookstore.service;

import com.example.bookstore.dto.CartDTO;
import com.example.bookstore.exception.BookStoreException;
import com.example.bookstore.exception.CartException;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Cart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CartRepository;
import com.example.bookstore.repository.UserRegistrationRepository;
import com.example.bookstore.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartService implements iCartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    iUserService iUserService;
    @Autowired
    iBookService iBookService;
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    TokenUtil tokenUtil;


    @Override
    public List<Cart> getCartData() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getCartDataById(int cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new CartException("Cart With id " + cartId + " note Found "));
    }

    @Override
    public Cart insertItems(CartDTO cartDTO,String token) throws BookStoreException {
        int id = tokenUtil.decodeToken(token);
        Optional<User> user = userRegistrationRepository.findById(cartDTO.getUserId());
        Optional<Book> bookDetails = bookRepository.findById(cartDTO.getBookId());
        Cart cartDetails;
        if (user.isPresent() && bookDetails.isPresent()) {
            if (cartDTO.getQuantity() > 0 && bookDetails.get().getQty() >= cartDTO.getQuantity()) {
                Cart cartDetails1 = cartRepository.findByUserId(cartDTO.getUserId(), cartDTO.getBookId());
                if (cartDetails1 != null) {
                    cartDetails = updateCarts(cartDetails1.getId(), cartDTO);
                    return cartDetails;
                } else {
                    cartDetails = new Cart(user.get(), bookDetails.get(), cartDTO.getQuantity());
                    return cartRepository.save(cartDetails);
                }
            } else {
                throw new BookStoreException("Book Quantity is not available as per order !!");
            }
        } else {
            throw new BookStoreException("Book Or User Might be not Present !!!");
        }
    }

    private Cart updateCarts(int id, CartDTO cartDTO) {
        if (cartRepository.findById(id).isPresent()) {
            Optional<Cart> cartDetails = cartRepository.findById(id);
            cartDetails.get().setQuantity(cartDetails.get().getQuantity() + cartDTO.getQuantity());
            cartDetails.get().setPrice(cartDetails.get().calculatePrice());
            return cartRepository.save(cartDetails.get());
        } else {
            throw new BookStoreException("Record not available of provided id");
        }
    }

    @Override
    public Cart updateCartItems(Integer cartID, CartDTO cartdto) {
        Optional<User> user = userRegistrationRepository.findById(cartdto.getUserId());
        Optional<Book> bookDetails = bookRepository.findById(cartdto.getBookId());
        if (cartRepository.findById(cartID).isPresent()) {
            if (user.isPresent() && bookDetails.isPresent()) {
                Optional<Cart> cartDetails = cartRepository.findById(cartID);
                cartDetails.get().setPrice(cartDetails.get().calculatePrice());
                cartDetails.get().setQuantity(cartdto.getQuantity());
                cartRepository.save(cartDetails.get());
                return cartDetails.get();
            } else {
                throw new BookStoreException("User ID Or Book ID Not Found");
            }
        }
        throw new BookStoreException("Record not available of provided id");
    }

    @Override
    public Cart deleteCartItems(Integer cartID) {
        Optional<Cart> cart = cartRepository.findById(cartID);
        Optional<Book> book = bookRepository.findById(cart.get().getBook().getId());
        if (cart.isEmpty()) {
            throw new CartException("Cart Record doesn't exists");
        } else {
            book.get().setQty(book.get().getQty() + cart.get().getQuantity());
            bookRepository.save(book.get());
            cartRepository.deleteById(cartID);
            log.info("Cart record deleted successfully for id " + cartID);
            return cart.get();
        }
    }
@Override
    public Cart updateQuantityItems(Integer cartID, Integer quantity) {
        Optional<Cart> cart = cartRepository.findById(cartID);
        Optional<Book> book = bookRepository.findById(cart.get().getBook().getId());
        if (cart.isEmpty()) {
            throw new CartException("Cart Record doesn't exists");
        } else {
            if (quantity <= book.get().getQty()) {
                cart.get().setQuantity(quantity);
                cartRepository.save(cart.get());
                log.info("Quantity in cart record updated successfully");
                return cart.get();
            } else {
                throw new CartException("Requested quantity is not available");
            }
        }
    }

}
