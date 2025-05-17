package com.alkhatib.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.alkhatib.dto.OrderDto;
import com.alkhatib.entities.Order;
import com.alkhatib.entities.User;
import com.alkhatib.repositories.OrderRepository;
import com.alkhatib.repositories.UserRepository;

@Service
public class OrderServiceD {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceD(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // Get all orders of a user
    public List<OrderDto> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Get single order by ID
    public Optional<OrderDto> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::convertToDto);
    }

    // Create new order for a user
    public OrderDto createOrderForUser(Long userId, OrderDto orderRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Order order = new Order(orderRequestDto.getDescription(), user);
        Order savedOrder = orderRepository.save(order);

        return convertToDto(savedOrder);
    }

    // Update an order
    public OrderDto updateOrder(Long id, OrderDto orderDetailsDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setDescription(orderDetailsDto.getDescription());
        Order updatedOrder = orderRepository.save(order);

        return convertToDto(updatedOrder);
    }

    // Delete an order
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // --- Helper method to map Entity to DTO ---
    private OrderDto convertToDto(Order order) {
        return new OrderDto(order.getId(), order.getDescription(), order.getUser().getId());
    }
}