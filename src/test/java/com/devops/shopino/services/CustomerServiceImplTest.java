package com.devops.shopino.services;

import com.devops.shopino.repositories.ICustomerRepository;
import com.devops.shopino.services.interfaces.ICustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    private ICustomerService underTest;
    @Mock
    private ICustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
    }
}