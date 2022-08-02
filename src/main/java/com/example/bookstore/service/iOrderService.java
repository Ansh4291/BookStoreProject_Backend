package com.example.bookstore.service;

import com.example.bookstore.dto.OrderDTO;
import com.example.bookstore.model.Order;

import java.util.List;

public interface iOrderService {

    Order placeOrder(OrderDTO orderDTO, String token);

    Order getOrderItems(Integer orderID);

    List<Order> getAllOrderItems();

    void CancleOrderItem(int id);
//    Order insertOrderItem(OrderDTO orderDTO);

//    Order insertOrderItem(OrderDTO orderDTO);
}
