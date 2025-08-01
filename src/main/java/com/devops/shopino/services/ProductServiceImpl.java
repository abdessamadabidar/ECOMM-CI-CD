package com.devops.shopino.services;

import com.devops.shopino.dto.request.ProductRequest;
import com.devops.shopino.dto.response.ProductResponseDto;
import com.devops.shopino.entities.Category;
import com.devops.shopino.entities.Product;
import com.devops.shopino.exceptions.ProductAlreadyExists;
import com.devops.shopino.exceptions.ProductNotFoundException;
import com.devops.shopino.mappers.IProductMapper;
import com.devops.shopino.repositories.IProductRepository;
import com.devops.shopino.services.interfaces.ICategoryService;
import com.devops.shopino.services.interfaces.IProductService;
import com.devops.shopino.utils.StringGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements IProductService {
    private final IProductRepository productRepository;
    private final ICategoryService categoryService;
    private final IProductMapper mapper;
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public Page<ProductResponseDto> getPagedProducts(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pr);
        List<ProductResponseDto> mappedProsuctsList = mapper
                .productsToProductResponses(
                        productPage.getContent()
                );
        return new PageImpl<>(mappedProsuctsList, pr, productPage.getTotalElements());
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return mapper.productsToProductResponses(productRepository.findAll());
    }

    @Override
    public ProductResponseDto registerProduct(ProductRequest request) {
        Optional<Product> optionalProduct = productRepository
                .findProductByName(request.getName());

        if (optionalProduct.isPresent()) {
            LOGGER.error("Product {} already exist!", request.getName());
            throw new ProductAlreadyExists("Product already exist");
        }

        Category category = categoryService.findById(request.getCategoryId());

        Product newProduct = Product
                .builder()
                .name(request.getName())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .price(request.getPrice())
                .reference(StringGenerator.generate(9, 5, 14))
                .availableQuantity(request.getAvailableQuantity())
                .category(category)
                .build();

        Product savedProduct = productRepository.save(newProduct);

        return mapper.productToProductResponse(savedProduct);
    }

    @Override
    public List<ProductResponseDto> registerProducts(List<ProductRequest> requests) {
        List<Product> products = new ArrayList<>();
        requests.forEach(
                        pr -> {
                            // check whether the product already exist
                            Optional<Product> optionalProduct = productRepository
                                    .findProductByName(pr.getName());

                            if (optionalProduct.isPresent()) {
                                LOGGER.error("Product {} already exist!", pr.getName());
                                throw new ProductAlreadyExists("Product already exist");
                            }


                            // load category
                            Category category = categoryService.findById(pr.getCategoryId());

                            Product newProduct = Product
                                    .builder()
                                    .name(pr.getName())
                                    .description(pr.getDescription())
                                    .imageUrl(pr.getImageUrl())
                                    .price(pr.getPrice())
                                    .reference(StringGenerator.generate(9, 5, 14))
                                    .availableQuantity(pr.getAvailableQuantity())
                                    .category(category)
                                    .build();


                            products.add(newProduct);

                        });

        List<Product> savedProducts = productRepository.saveAll(products);
        return mapper.productsToProductResponses(savedProducts);
    }

    @Override
    public Product getProductById(UUID id) {
        return productRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            LOGGER.error("Product with id {} not found!", id);
                            return new ProductNotFoundException(
                                    "Product not found!");
                        }
                );
    }

    @Override
    public ProductResponseDto searchProductById(UUID id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            LOGGER.error("Product with id {} not found!", id);
                            return new ProductNotFoundException(
                                    "Product not found!");
                        }
                );

        return mapper.productToProductResponse(product);
    }

    @Override
    public Long countProducts() {
        return productRepository.count();
    }
}
