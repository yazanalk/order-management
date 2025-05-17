package com.alkhatib.services;

import org.springframework.stereotype.Service;

import com.alkhatib.entities.Order;
import com.alkhatib.entities.User;
import com.alkhatib.repositories.OrderRepository;
import com.alkhatib.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService_old {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService_old(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

   

    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setDescription(orderDetails.getDescription());
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
    
    public Order createOrderForUser(Long userId, Order orderRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        orderRequest.setUser(user);

        return orderRepository.save(orderRequest);
    }
}
