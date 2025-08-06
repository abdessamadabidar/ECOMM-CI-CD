package com.devops.shopino.controllers;

import com.devops.shopino.dto.response.OrderResponseDto;
import com.devops.shopino.services.interfaces.IOrderService;
import com.devops.shopino.utils.PageInfo;
import com.devops.shopino.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/graphql")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderGraphqlController {
    private final IOrderService orderService;


    @QueryMapping
    public OrderResponseDto orderById(
            @Argument UUID id
            ){
        return orderService.searchOrderById(id);
    }

    @QueryMapping
    public PageResponse<OrderResponseDto> paginatedOrders(
            @Argument int page,
            @Argument int size
    ) {

        Page<OrderResponseDto> ordersPage = orderService.getPagedOrders(page, size);

        return PageResponse.<OrderResponseDto>builder()
                .content(ordersPage.getContent())
                .pageInfo(
                        PageInfo
                                .builder()
                                .totalPages(ordersPage.getTotalPages())
                                .totalElements(ordersPage.getTotalElements())
                                .currentPage(ordersPage.getNumber() + 1)
                                .size(ordersPage.getSize())
                                .hasNext(ordersPage.hasNext())
                                .hasPrevious(ordersPage.hasPrevious())
                                .build()
                )
                .build();
    }
}
