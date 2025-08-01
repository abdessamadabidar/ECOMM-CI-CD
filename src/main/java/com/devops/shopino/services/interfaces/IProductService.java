package com.devops.shopino.services.interfaces;

import com.devops.shopino.dto.request.ProductRequest;
import com.devops.shopino.dto.response.ProductResponseDto;
import com.devops.shopino.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface IProductService {
    Page<ProductResponseDto> getPagedProducts(int page, int size);
    List<ProductResponseDto> getAllProducts();
    ProductResponseDto registerProduct(ProductRequest request);
    List<ProductResponseDto> registerProducts(List<ProductRequest> requests);
    Product getProductById(UUID id);
    ProductResponseDto  searchProductById(UUID id);
    Long countProducts();

}
