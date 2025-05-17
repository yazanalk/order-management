package com.alkhatib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alkhatib.entities.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}