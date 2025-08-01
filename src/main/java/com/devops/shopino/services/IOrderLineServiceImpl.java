package com.devops.shopino.services;

import com.devops.shopino.dto.response.ProductResponseDto;
import com.devops.shopino.entities.Category;
import com.devops.shopino.entities.OrderLine;
import com.devops.shopino.repositories.IOrderLineRepository;
import com.devops.shopino.services.interfaces.IOrderLineService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IOrderLineServiceImpl implements IOrderLineService {

    private final IOrderLineRepository orderLineRepository;


    @Override
    public OrderLine registerOrderLine(OrderLine orderLine) {
        return orderLineRepository.save(orderLine);
    }

    @Override
    public Map<ProductResponseDto, Double> getBestSellingProducts(int limit) {

        if (limit < 0) {
            throw new IllegalArgumentException(
                    "query limit argument cannot be negative"
            );
        }

        Map<ProductResponseDto, Double> bestSellingProductsMap = new HashMap<>();
        orderLineRepository.getBestSellingProducts(limit)
                .forEach(
                        tuple -> {
                            bestSellingProductsMap
                                    .put(
                                            ProductResponseDto
                                                    .builder()
                                                    .id(tuple.get("product_id", UUID.class))
                                                    .name(tuple.get("product_name", String.class))
                                                    .category(Category
                                                            .builder()
                                                            .id(null)
                                                            .name(tuple.get("category_name", String.class))
                                                            .build())
                                                    .price(tuple.get("price", Double.class))
                                                    .availableQuantity(tuple.get("available_quantity", Integer.class))
                                                    .reference(tuple.get("reference", String.class))
                                                    .description(null)
                                                    .build(),
                                            tuple.get("amount", Double.class)
                                    );
                        }
                );


        return bestSellingProductsMap;
    }
}
