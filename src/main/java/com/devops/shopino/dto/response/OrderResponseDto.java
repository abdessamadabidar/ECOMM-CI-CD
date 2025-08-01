package com.devops.shopino.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {
    private UUID id;
    private LocalDateTime orderedAt;
    private String reference;
    private Double totalAmount;
    private CustomerResponseDto customer;
    private List<OrderLineResponseDto> orderLines;
}
