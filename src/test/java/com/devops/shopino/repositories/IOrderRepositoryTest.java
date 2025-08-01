package com.devops.shopino.repositories;

import com.devops.shopino.entities.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class IOrderRepositoryTest {

    @Autowired
    private IOrderRepository underTest;
    private Order order;


    @BeforeEach
    void setUp() {
        this.order = Order
                .builder()
                .orderedAt(LocalDateTime.now())
                .orderLines(Collections.emptyList())
                .reference("HS58SQ2322AW5P")
                .totalAmount(17892.45)
                .customer(null)
                .build();
    }


    @Test
    void testSave_WhenOrderIsNotNull_ShouldReturnOrder() {

        // Given
        Order expectedResult = this.order;

        // When
        Order actualResult = this.underTest.save(expectedResult);

        // Assert
        assertNotNull(actualResult);
        assertEquals(
                expectedResult,
                actualResult,
                () -> "Expected order does not match the actual order"
        );

    }

    @Test
    void testSave_WhenOrderIsNull_ShouldThrowException() {
        assertThrows(
                Exception.class,
                () -> underTest.save(null)
        );
    }

    @Test
    void testFindById_WhenOrderExists_ShouldReturnOrder() {

        // Given
        Order expectedResult = this.order;
        this.underTest.save(expectedResult);

        // When
        Optional<Order> optionalActualResult = this.underTest.findById(expectedResult.getId());

        // Assert
        assertTrue(optionalActualResult.isPresent());
        assertEquals(
                expectedResult,
                optionalActualResult.get(),
                () -> "Expected order does not match the actual order"
        );
    }

    @Test
    void testFindById_WhenOrderDoesNotExists_ShouldReturnEmpty() {
        // Given
        Order expectedResult = this.order;
        this.underTest.save(expectedResult);
        UUID nonExistingOrderId = UUID.randomUUID();

        // When
        Optional<Order> optionalActualResult = this.underTest.findById(nonExistingOrderId);

        // Assert
        assertTrue(optionalActualResult.isEmpty());

    }
}