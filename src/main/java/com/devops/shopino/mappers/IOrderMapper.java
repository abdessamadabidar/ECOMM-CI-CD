package com.devops.shopino.mappers;


import com.devops.shopino.dto.response.OrderResponseDto;
import com.devops.shopino.entities.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IOrderMapper {
    OrderResponseDto orderToOrderResponse(Order order);
    List<OrderResponseDto> ordersToOrderResponses(List<Order> orders);
}


