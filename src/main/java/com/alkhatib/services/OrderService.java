package com.alkhatib.services;

import java.util.List;


import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alkhatib.dto.OrderDto;
import com.alkhatib.entities.Order;
import com.alkhatib.entities.User;
import com.alkhatib.exception.UserNotFoundException;
import com.alkhatib.mappers.OrderMapper;
import com.alkhatib.repositories.OrderRepository;
import com.alkhatib.repositories.UserRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // Get all orders of a user
    public List<OrderDto> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }
    
    
    public List<OrderDto> getOrdersByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return getOrdersByUserId(user.getId()); // Reuse existing method
    }
    
    

    // Get single order by ID
    public Optional<OrderDto> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderMapper::toDto);
    }

    // Create new order for a user
    public OrderDto createOrderForUser2(Long userId, OrderDto orderRequestDto) {
    	
    	User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Order order = new Order(orderRequestDto.getDescription(), user);
        Order savedOrder = orderRepository.save(order);
        
        
        
        return OrderMapper.toDto(savedOrder);
    }
    
    public OrderDto createOrderForUser(Long userId, OrderDto orderRequest) {
    	System.out.println("this one now createOrderForUser2");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Order order = OrderMapper.toEntity(orderRequest,user);
        order.setUser(user);

        Order saved = orderRepository.save(order);
        return OrderMapper.toDto(saved);
    }

    // Update an order
    public OrderDto updateOrder(Long id, OrderDto orderDetailsDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setDescription(orderDetailsDto.getDescription());
        Order updatedOrder = orderRepository.save(order);

         return OrderMapper.toDto(updatedOrder);
    }
    
    public OrderDto updateOrderForUser(Long id, OrderDto orderDetailsDto, String username) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Ensure the logged-in user owns the order
        if (!order.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not authorized to update this order.");
        }

        // Proceed with the update
        order.setDescription(orderDetailsDto.getDescription());
        // Add more fields if needed
        Order updatedOrder = orderRepository.save(order);

        return OrderMapper.toDto(updatedOrder);
    }

    // Delete an order
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
    
    public void deleteOrderForUser(Long orderId, String username) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getUsername().equals(username)) {
            System.out.println("here throw!");
        	throw new AccessDeniedException("You are not authorized to delete this order.");
            
        }

        orderRepository.delete(order);
    }
    
    public OrderDto createOrderForUsername(String username, OrderDto orderRequest) {
        User user = userRepository.findByUsername(username)
                      .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return createOrderForUser(user.getId(), orderRequest); // reuse existing logic
    }

   
}