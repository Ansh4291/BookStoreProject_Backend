package com.example.bookstore.model;

import com.example.bookstore.dto.CartDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;

@Data
@RequiredArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
    private int quantity;
    private double price;

    public Cart(int id, User user, Book book, int quantity) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.quantity = quantity;
    }

//    public Cart(Book bookData, User user, int quantity) {
//        this.book = bookData;
//        this.user = user;
//        this.quantity = quantity;
//    }

    public Cart(User user, Book book, int quantity) {
        this.user = user;
        this.book = book;
        this.quantity = quantity;
        this.price = calculatePrice();
    }

    public Cart(int quantity, Book book, User user) {
        this.quantity = quantity;
        this.book = book;
        this.user = user;
    }

    public void updateBookData(CartDTO cartDTO) {
        this.user =user;
        this.book = book;
        this.quantity = quantity;
    }
    public double calculatePrice(){
        double totalPrice = this.getQuantity() * this.book.getPrice();
        return totalPrice;
    }


}
