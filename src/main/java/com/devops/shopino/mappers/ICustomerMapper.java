package com.devops.shopino.mappers;

import com.devops.shopino.dto.request.CustomerRequest;
import com.devops.shopino.dto.response.CustomerResponseDto;
import com.devops.shopino.entities.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICustomerMapper {
    CustomerResponseDto customerToCustomerResponse(Customer customer);
    List<CustomerResponseDto> customersToCustomerResponses(List<Customer> customers);
    Customer customerRequestToCustomer(CustomerRequest request);
    List<Customer> customerRequestsToCustomers(List<CustomerRequest> requests);
}

