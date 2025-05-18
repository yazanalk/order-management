package com.alkhatib.controllers;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alkhatib.dto.OrderDto;
import com.alkhatib.services.OrderService;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")

public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
    	
        this.orderService = orderService;
    }

    @PostConstruct
    public void init() {
    	System.out.println("the new one is now used");
    }
    
    
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderDto>> getMyOrders(Authentication authentication) {
        String username = authentication.getName(); // Get username from JWT
        List<OrderDto> orders = orderService.getOrdersByUsername(username);
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }
    
    
    // Get all orders of a user, ResponseEntity
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUser(@PathVariable Long userId) {
        List<OrderDto> orders = orderService.getOrdersByUserId(userId);

        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        }
        
        System.out.println("the new one is now used");
        
        return ResponseEntity.ok(orders); // 200 OK with list of orders
    }

    // Get all orders of a user, just List
    @GetMapping("/user/list/{userId}")
    public List<OrderDto> getOrdersByUserList(@PathVariable Long userId) {
    	
    	 System.out.println("the new one is now used");
    	
        return orderService.getOrdersByUserId(userId);
    }

    // Create new order for a user
    @PostMapping("/user/{userId}")
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long userId,  @Valid @RequestBody OrderDto orderRequest) {
        OrderDto createdOrder = orderService.createOrderForUser(userId, orderRequest);
        
        System.out.println("the create new one is now used");
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
    
    @PostMapping("/my-orders/create")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderRequest, Authentication authentication) {
        String username = authentication.getName(); // Get the logged-in user's username
        OrderDto createdOrder = orderService.createOrderForUsername(username, orderRequest);

        System.out.println("Order created for authenticated user");

        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
    
    // Update an existing order
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody OrderDto orderDto) {
        OrderDto updatedOrder = orderService.updateOrder(orderId, orderDto);
        
        System.out.println("the new one is now used");
        return ResponseEntity.ok(updatedOrder);
    }
    
    @PutMapping("/update/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(
            @PathVariable Long orderId,
            @RequestBody OrderDto orderDto,
            Authentication authentication) {

        String username = authentication.getName(); // Logged-in user's username

        // Let the service check if this user owns the order
        OrderDto updatedOrder = orderService.updateOrderForUser(orderId, orderDto, username);

        System.out.println("Order update restricted to owner only");
        return ResponseEntity.ok(updatedOrder);
    }
    

    // Delete an order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        
        System.out.println("the new one is now used");
        
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("delete/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Long orderId,
            Authentication authentication) {

        String username = authentication.getName();
        orderService.deleteOrderForUser(orderId, username);

        System.out.println("Order deleted by its owner");

        return ResponseEntity.noContent().build();
    }
}