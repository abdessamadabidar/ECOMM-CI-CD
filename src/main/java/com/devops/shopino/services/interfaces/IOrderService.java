package com.devops.shopino.services.interfaces;


import com.devops.shopino.dto.request.OrderRequest;
import com.devops.shopino.dto.response.OrderResponseDto;
import org.springframework.data.domain.Page;

import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IOrderService {
    Page<OrderResponseDto> getPagedOrders(int page, int size);
    OrderResponseDto makeOrder(OrderRequest request);
    OrderResponseDto searchOrderById(UUID id);
    Long countOrders();
    Double computeTotalIncome();
    Map<Integer, Double> incomeVariationOfTheYear();
}
