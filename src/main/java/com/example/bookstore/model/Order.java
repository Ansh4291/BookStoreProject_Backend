package com.example.bookstore.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "OrderDetails")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    @OneToMany(fetch = FetchType.LAZY,orphanRemoval=true)
    @org.hibernate.annotations.ForeignKey(name = "none")
    private List<Cart> cart;
    @ManyToMany
    private List<Book> book;
    private int userId;
    private int orderQty;
    private String address;
    private double orderPrice;
    private LocalDate orderedAt;
    private boolean cancel;

    public Order(int userId, String address, List<Cart> cart, List<Book> book, LocalDate orderedAt, int totalOrderQty, double totalPrice, boolean cancel) {
        this.userId=userId;
        this.cart=cart;
        this.orderedAt=orderedAt;
        this.orderQty=totalOrderQty;
        this.orderPrice=totalPrice;
        this.cancel=cancel;
        this.address=address;
        this.book=book;
    }
}
