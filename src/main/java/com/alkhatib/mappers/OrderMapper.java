package com.alkhatib.mappers;

import com.alkhatib.dto.OrderDto;
import com.alkhatib.entities.Order;
import com.alkhatib.entities.User;

public class OrderMapper {

    // Entity -> DTO
    public static OrderDto toDto(Order order) {
        if (order == null) {
            return null;
        }

        return new OrderDto(
                order.getId(),
                order.getDescription(),
                order.getUser() != null ? order.getUser().getId() : null
        );
    }

    // DTO -> Entity
    public static Order toEntity(OrderDto orderDto, User user) {
        if (orderDto == null) {
            return null;
        }

        Order order = new Order();
        order.setId(orderDto.getId()); // usually set only for update cases
        order.setDescription(orderDto.getDescription());
        order.setUser(user);

        return order;
    }
}