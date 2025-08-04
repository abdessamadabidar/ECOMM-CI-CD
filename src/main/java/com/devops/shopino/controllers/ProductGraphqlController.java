package com.devops.shopino.controllers;

import com.devops.shopino.dto.response.ProductResponseDto;
import com.devops.shopino.services.interfaces.IProductService;
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
public class ProductGraphqlController {
    private final IProductService productService;


    @QueryMapping
    public ProductResponseDto productById(
            @Argument UUID id
            ) {
        return productService.searchProductById(id);
    }

    @QueryMapping
    public PageResponse<ProductResponseDto> paginatedProducts(
            @Argument int page,
            @Argument int size
    ) {
        Page<ProductResponseDto> productsPage = productService.getPagedProducts(page, size);

        return PageResponse.<ProductResponseDto>builder()
                .content(productsPage.getContent())
                .pageInfo(
                        PageInfo
                                .builder()
                                .totalPages(productsPage.getTotalPages())
                                .totalElements(productsPage.getTotalElements())
                                .currentPage(productsPage.getNumber() + 1)
                                .size(productsPage.getSize())
                                .hasNext(productsPage.hasNext())
                                .hasPrevious(productsPage.hasPrevious())
                                .build()
                )
                .build();
    }
}
