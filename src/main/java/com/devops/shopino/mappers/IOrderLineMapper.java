package com.devops.shopino.mappers;

import com.devops.shopino.dto.response.OrderLineResponseDto;
import com.devops.shopino.entities.OrderLine;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IOrderLineMapper {
    OrderLineResponseDto orderLineToOrderLineResponse(OrderLine orderLine);
    List<OrderLineResponseDto> orderLinesToOrderLineResponses(List<OrderLine> orderLines);
}
