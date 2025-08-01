package com.devops.shopino.controllers;


import com.devops.shopino.dto.response.CustomerResponseDto;
import com.devops.shopino.services.interfaces.ICustomerService;
import com.devops.shopino.utils.PageInfo;
import com.devops.shopino.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/graphql")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CustomerGraphQLController {

    private final ICustomerService customerService;

    @QueryMapping
    public CustomerResponseDto customerById(
            @Argument UUID id
            )
    {
        return customerService.searchCustomerById(id);
    }

    @QueryMapping
    public PageResponse<CustomerResponseDto> paginatedCustomers(
            @Argument int page,
            @Argument int size
    ){
        Page<CustomerResponseDto> customerPage = customerService.getPagedCustomers(page, size);

        return PageResponse.<CustomerResponseDto>builder()
                .content(customerPage.getContent())
                .pageInfo(
                        PageInfo
                                .builder()
                                .totalPages(customerPage.getTotalPages())
                                .totalElements(customerPage.getTotalElements())
                                .currentPage(customerPage.getNumber() + 1)
                                .size(customerPage.getSize())
                                .hasNext(customerPage.hasNext())
                                .hasPrevious(customerPage.hasPrevious())
                                .build()
                )
                .build();
    }


    @MutationMapping
    public ResponseEntity<String> deleteCustomer(@Argument UUID id) {
        return new ResponseEntity<>(customerService.removeCustomer(id), HttpStatus.NO_CONTENT);
    }
}
