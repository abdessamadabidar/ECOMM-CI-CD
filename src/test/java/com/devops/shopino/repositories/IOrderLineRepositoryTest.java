package com.devops.shopino.repositories;

import com.devops.shopino.entities.OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class IOrderLineRepositoryTest {
    @Autowired
    private IOrderLineRepository underTest;
    private OrderLine orderLine;


    @BeforeEach
    void setUp() {
        this.orderLine = OrderLine
                .builder()
                .product(null)
                .order(null)
                .quantity(4)
                .build();

    }

    @Test
    void testSave_WhenOrderLineIsNotNull_ShouldReturnOrderLine() {
        // Given
        OrderLine expectedResult = this.orderLine;
        // When
        OrderLine actualResult = this.underTest.save(expectedResult);
        // Assert
        assertNotNull(actualResult);
        assertEquals(
                expectedResult,
                actualResult,
                () -> "Expected order line does not match the actual order line"
        );
    }

    @Test
    void testSave_WhenOrderLineIsNull_ShouldThrowException() {
        assertThrows(
                Exception.class,
                () -> underTest.save(null)
        );
    }


}