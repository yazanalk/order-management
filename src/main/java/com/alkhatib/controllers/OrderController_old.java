package com.alkhatib.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.alkhatib.dto.OrderDto;
import com.alkhatib.entities.Order;
import com.alkhatib.services.OrderService_old;

import jakarta.annotation.PostConstruct;

import java.util.List;

@RestController
@RequestMapping("/api/old/orders")
public class OrderController_old {

    private final OrderService_old orderService;

    public OrderController_old(OrderService_old orderService) {
        this.orderService = orderService;
    }

    
    @PostConstruct
    public void init() {
    	System.out.println("the old one is now used");
    }
    
    
    
   
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
    	
    	 List<Order> orders = orderService.getOrdersByUserId(userId);
    	    
    	    if (orders.isEmpty()) {
    	        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    	    }

    	    return ResponseEntity.ok(orders); // 200 OK with the list of orders
    }
    
    
    
    @GetMapping("/user/list/{userId}")
    public List<Order> getOrdersByUserList(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Order> createOrder(@PathVariable Long userId, @RequestBody Order orderRequest) {
        Order createdOrder = orderService.createOrderForUser(userId, orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order order) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, order));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}