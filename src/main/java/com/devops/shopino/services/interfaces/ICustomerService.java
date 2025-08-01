package com.devops.shopino.services.interfaces;

import com.devops.shopino.dto.request.CustomerRequest;
import com.devops.shopino.dto.response.CustomerResponseDto;
import com.devops.shopino.entities.Customer;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ICustomerService {
    Page<CustomerResponseDto> getPagedCustomers(int page, int size);
    List<CustomerResponseDto> getAllCustomers();
    CustomerResponseDto registerCustomer(CustomerRequest customerRequest);
    List<CustomerResponseDto> registerCustomers(List<CustomerRequest> customerRequestList);
    Customer getCustomerById(UUID id);
    CustomerResponseDto searchCustomerById(UUID id);
    String removeCustomer(UUID id);
    Long countCustomers();


}
