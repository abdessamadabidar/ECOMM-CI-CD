package com.devops.shopino.repositories;

import com.devops.shopino.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findProductByName(String name);
}
