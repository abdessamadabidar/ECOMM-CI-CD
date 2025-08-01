package com.devops.shopino.repositories;

import com.devops.shopino.entities.Product;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class IProductRepositoryTest {

    @Autowired
    private IProductRepository underTest;
    private Faker faker = new Faker();
    private Product product;

    @BeforeEach
    void setUp() {
        this.product = Product
                .builder()
                .name(faker.commerce().productName())
                .reference("GJ85WCX0255LKB")
                .description(faker.lorem().sentence(10))
                .imageUrl("https://prd.place/320?id=20?padding=100")
                .price(Double.parseDouble(faker.commerce().price(20, 200)))
                .availableQuantity(faker.random().nextInt(10, 99))
                .category(null)
                .build();

    }

    @Test
    void testSave_WhenSaveProduct_ShouldReturnProduct() {
        // Given
        Product expectedResult = this.product;

        // When
        Product actualProduct = this.underTest.save(expectedResult);

        // Assert
        assertNotNull(actualProduct);
        assertEquals(
                expectedResult,
                actualProduct,
                () -> "Expected product does not match the actual product"
        );
    }


    @Test
    void testSave_WhenProductIsNull_ShouldThrowException() {
        assertThrows(
                Exception.class,
                () -> underTest.save(null)
        );
    }

    @Test
    void testFindById_WhenProductExists_ShouldReturnProduct() {

        // Given
        Product expectedProduct = this.product;
        this.underTest.save(expectedProduct);

        // When
        Optional<Product> optionalActualResult =  this.underTest.findById(expectedProduct.getId());

        // Assert
        assertTrue(optionalActualResult.isPresent());
        assertEquals(
                expectedProduct,
                optionalActualResult.get(),
                () -> "Expected product does not match the actual product"
        );

    }

    @Test
    void testFindById_WhenProductDoesNotExist_ShouldReturnEmpty() {

        // Given
        Product expectedResult = this.product;
        this.underTest.save(expectedResult);
        UUID nonExistProductId = UUID.randomUUID();

        // When
        Optional<Product> optionalActualResult =  this.underTest.findById(nonExistProductId);

        // Assert
        assertTrue(optionalActualResult.isEmpty());
    }




    @Test
    void testFindByName_WhenProductExists_ShouldReturnProduct() {

        // Given
        Product expectedResult = this.product;
        this.underTest.save(expectedResult);

        // When
        Optional<Product> optionalActualResult = this.underTest.findProductByName(expectedResult.getName());

        // Assert
        assertTrue(optionalActualResult.isPresent());
        assertEquals(
                expectedResult,
                optionalActualResult.get(),
                () -> "Expected product does not match the actual product"
        );
    }

    @Test
    void testFindByName_WhenProductDoesNotExist_ShouldReturnEmpty() {

        // Given
        Product expectedResult = this.product;
        this.underTest.save(expectedResult);
        String nonExistingProductName = this.faker.commerce().productName();


        // When
        Optional<Product> optionalActualResult = this.underTest.findProductByName(nonExistingProductName);

        // Assert
        assertTrue(optionalActualResult.isEmpty());


    }
}