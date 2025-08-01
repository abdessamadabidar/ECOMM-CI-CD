package com.devops.shopino.repositories;

import com.devops.shopino.dto.request.CustomerRequest;
import com.devops.shopino.entities.Address;
import com.devops.shopino.entities.Customer;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
class ICustomerRepositoryTest {

    @Autowired
    private ICustomerRepository underTest;
    private final Faker faker = new Faker();
    private Customer customer;


    @BeforeEach
    void setUp() {
        this.customer = Customer
                .builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .phone(faker.phoneNumber().cellPhone())
                .email(faker.internet().emailAddress())
                .address(
                        Address
                                .builder()
                                .zipCode(Long.valueOf(faker.address().zipCode()))
                                .houseNumber(Integer.valueOf(faker.address().buildingNumber()))
                                .street(faker.address().streetAddress())
                                .city(faker.address().cityName())
                                .build()
                )
                .build();
    }

    @Test
    void testSave_WhenSaveCustomer_ShouldReturnCustomer() {
        // Given
        Customer expectedResult = this.customer;

        // When
        Customer actualResult = underTest.save(expectedResult);

        // Assert
        assertNotNull(actualResult);
        assertEquals(
                expectedResult,
                actualResult,
                () -> "Expected customer does not match the actual customer"
        );

    }


    @Test
    void testSave_WhenCustomerIsNull_ShouldThrowException() {
        assertThrows(
                Exception.class,
                () -> underTest.save(null)
        );
    }


    @Test
    void testFindById_WhenCustomerExists_ShouldReturnCustomer() {

        // Given
        Customer expectedResult = this.customer;
        underTest.save(expectedResult);

        // When
        Optional<Customer> actualResultOptional = underTest.findById(expectedResult.getId());

        // Assert
        assertTrue(actualResultOptional.isPresent());
        assertEquals(
                expectedResult,
                actualResultOptional.get(),
                () -> "The customer founded does not match the actual customer"
        );

    }

    @Test
    void testFindById_WhenCustomerDoesNotExist_ShouldReturnEmpty() {
        // Given
        Customer expectedResult = this.customer;
        underTest.save(expectedResult);
        UUID nonExistCustomerId = UUID.randomUUID();

        // When
        Optional<Customer> actualResultOptional = underTest.findById(nonExistCustomerId);

        // Assert
        assertTrue(actualResultOptional.isEmpty());

    }

    @Test
    void testFindByEmail_WhenEmailExists_ShouldReturnCustomer() {

        // Given
        Customer expectedResult = this.customer;
        underTest.save(expectedResult);

        // When
        Optional<Customer> actualResultOptional = underTest.findByEmail(expectedResult.getEmail());

        // Assert
        assertTrue(actualResultOptional.isPresent());
        assertEquals(
                expectedResult,
                actualResultOptional.get(),
                () -> "The customer with given does not match the actual customer"
        );
    }

    @Test
    void testFindByEmail_WhenEmailDoesNotExist_ShouldReturnEmpty() {

        // Given
        Customer expectedResult = this.customer;
        underTest.save(expectedResult);
        String nonExistingCustomerEmail = faker.internet().emailAddress();

        // When
        Optional<Customer> actualResultOptional = underTest.findByEmail(nonExistingCustomerEmail);

        // Assert
        assertTrue(actualResultOptional.isEmpty());

    }
}