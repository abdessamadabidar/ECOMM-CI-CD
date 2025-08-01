package com.devops.shopino.mappers;

import com.devops.shopino.dto.response.ProductResponseDto;
import com.devops.shopino.entities.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductMapper {
    ProductResponseDto productToProductResponse(Product product);
    List<ProductResponseDto> productsToProductResponses(List<Product> products);

}
