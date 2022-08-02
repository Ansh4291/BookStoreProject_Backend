package com.example.bookstore.controller;

import com.example.bookstore.dto.OrderDTO;
import com.example.bookstore.dto.ResponseDTO;
import com.example.bookstore.model.Order;
import com.example.bookstore.service.iOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    iOrderService service;

    @GetMapping("/getAllOrder")
    public ResponseEntity<ResponseDTO> getAllOrderItems() {
        List<Order> newOrders = service.getAllOrderItems();
        ResponseDTO responseDTO = new ResponseDTO("All records retrieved successfully !", newOrders);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getOrder/{orderID}")
    public ResponseEntity<ResponseDTO> getOrderItems(@PathVariable Integer orderID) {
        Order newOrders = service.getOrderItems(orderID);
        ResponseDTO responseDTO = new ResponseDTO("Record retrieved successfully !", newOrders);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }


    @PostMapping("/placeOrder/{token}")
    public ResponseEntity<ResponseDTO> placeOrder(@PathVariable String token,@RequestBody OrderDTO orderDTO) {
        Order order = service.placeOrder(orderDTO , token);
        ResponseDTO responseDTO = new ResponseDTO(" Order Placed Successfully.", order);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/cancelOrder/{orderID}")
    public ResponseEntity<ResponseDTO> deleteOrderItem(@PathVariable int orderID) {
        service.CancleOrderItem(orderID);
        ResponseDTO responseDTO = new ResponseDTO("Order Canceled successfully !", "Order Canceled " +orderID);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }
}