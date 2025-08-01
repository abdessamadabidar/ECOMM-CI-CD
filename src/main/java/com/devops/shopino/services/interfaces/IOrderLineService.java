package com.devops.shopino.services.interfaces;

import com.devops.shopino.dto.response.ProductResponseDto;
import com.devops.shopino.entities.OrderLine;


import java.util.Map;

public interface IOrderLineService {

    OrderLine registerOrderLine(OrderLine orderLine);
    Map<ProductResponseDto, Double> getBestSellingProducts(int limit);





}
