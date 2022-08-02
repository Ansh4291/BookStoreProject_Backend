package com.example.bookstore.service;

import com.example.bookstore.dto.OrderDTO;
import com.example.bookstore.exception.OrderException;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Cart;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CartRepository;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class OrderService implements iOrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    iUserService service;
    @Autowired
    BookRepository bookRepository;
    ArrayList<Order> orderList = new ArrayList<>();
    @Autowired
    iBookService iBookService;
    @Autowired
    TokenUtil tokenUtil;

    @Override
    public Order placeOrder(OrderDTO orderDTO, String token) {
        int id = tokenUtil.decodeToken(token);
        List<Cart> cart = cartRepository.findByUser(id);
        double totalOrderPrice = 0;
        int totalOrderQty = 0;
        List<Book> orderedBooks = new ArrayList<>();
        String address = "";
        for (int i=0; i<cart.size(); i++){
            totalOrderPrice += cart.get(i).getPrice();
            totalOrderQty += cart.get(i).getQuantity();
            orderedBooks.add(cart.get(i).getBook());
        }
        if (orderDTO.getAddress() == null){
            User user = service.getUserDataById(token);
            address = user.getAddress();
        }
        else
            address = orderDTO.getAddress();
        Order order = new Order(orderDTO.userId, address, cart, orderedBooks, LocalDate.now(), totalOrderQty, totalOrderPrice, false);
        orderList.add(order);
        orderRepository.save(order);
        for (int i=0; i<cart.size(); i++) {
            Book book = cart.get(i).getBook();
            int updatedQty = this.updateBookQty(book.getQty(), cart.get(i).getQuantity());
            book.setQty(updatedQty);
            cartRepository.deleteById(cart.get(i).getId());
        }
        return order;
    }
    private int updateBookQty(int bookQty, int bookQtyInCart) {
        int updatedQty = bookQty - bookQtyInCart;
        if (updatedQty <= 0)
            return 0;
        else
            return updatedQty;
    }
    @Override
    public Order getOrderItems(Integer orderID) {
        Optional<Order> orders = orderRepository.findById(orderID);
        if (orders.isEmpty()) {
            throw new OrderException("Order Item doesn't exists");
        } else {
            log.info("Order item get successfully for id " + orderID);
            return orders.get();
        }
    }

    @Override
    public List<Order> getAllOrderItems() {
        List<Order> orders = orderRepository.findAll();
        log.info(" Order Items get successfully");
        return orders;
    }

    @Override
    public void CancleOrderItem(int id) {
        Order order = this.getOrderItems(id);
        if (order.isCancel() == false) {
            orderRepository.updateCancel(id);
            List<Book> book = order.getBook();
            for (int j = 0; j < orderList.size(); j++) { //order ids
                if (orderList.get(j).getOrderId() == id) { //id is there or not
                    for (int i = 0; i < book.size(); i++) { // book size qty in order
                        Book updateBook = iBookService.getBookDataById(book.get(i).getId());
                        for (int k = 0; k < book.size(); k++) {// cart
                            int orderedBookQty = orderList.get(j).getCart().get(k).getQuantity(); //from cart qty
                            int orderedBookId = orderList.get(j).getCart().get(k).getBook().getId(); //book id
                            int bookId = updateBook.getId();
                            if (orderedBookId == bookId) {
                                int updatedQty = orderedBookQty + updateBook.getQty();
                                updateBook.setQty(updatedQty);
                                bookRepository.save(book.get(i));
                            }
                        }
                    }
                }
            }
        }
        else throw new OrderException("Order is already canceled!");
    }
}
