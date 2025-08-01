package com.devops.shopino.services;

import com.devops.shopino.dto.request.CustomerRequest;
import com.devops.shopino.dto.response.CustomerResponseDto;
import com.devops.shopino.entities.Customer;
import com.devops.shopino.exceptions.CustomerAlreadyRegisteredException;
import com.devops.shopino.exceptions.CustomerNotFoundException;
import com.devops.shopino.mappers.ICustomerMapper;
import com.devops.shopino.repositories.ICustomerRepository;
import com.devops.shopino.services.interfaces.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private final ICustomerRepository customerRepository;
    private final ICustomerMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public Page<CustomerResponseDto> getPagedCustomers(int page, int size) {
        Pageable pr = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAll(pr);
        List<CustomerResponseDto> mappedCustomersList = mapper
                .customersToCustomerResponses(
                        customerPage.getContent()
                );


        return new PageImpl<>(mappedCustomersList, pr, customerPage.getTotalElements());
    }

    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        return mapper.customersToCustomerResponses(customerRepository.findAll());
    }

    @Override
    public CustomerResponseDto registerCustomer(CustomerRequest customerRequest) {

        Optional<Customer> optionalCustomer = customerRepository
                .findByEmail(customerRequest.getEmail());
            if (optionalCustomer.isPresent()) {
                LOGGER.error("customer with email {} already registered!", customerRequest.getEmail());
                throw new CustomerAlreadyRegisteredException(
                        "Customer already registered!");
            }

        Customer registeredCustomer =  customerRepository
                .save(mapper.customerRequestToCustomer(customerRequest));

        return mapper.customerToCustomerResponse(registeredCustomer);
    }

    @Override
    public List<CustomerResponseDto> registerCustomers(List<CustomerRequest> customerRequestList) {
        customerRequestList
                .forEach(cr -> {
                    Optional<Customer> optionalCustomer = customerRepository.findByEmail(cr.getEmail());
                    if (optionalCustomer.isPresent()) {
                        LOGGER.error("customer with email {} already registered!", cr.getEmail());
                        throw new CustomerAlreadyRegisteredException(
                                "Customer already registered!");
                    }
                });

        List<Customer> savedCustomersList = customerRepository
                .saveAll(
                        mapper.customerRequestsToCustomers(customerRequestList)
                );

        return mapper.customersToCustomerResponses(savedCustomersList);
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            LOGGER.error("customer with id {} not found!", id);
                            return new CustomerNotFoundException(
                                    "Customer not found!");
                        }
                );
    }

    @Override
    public CustomerResponseDto searchCustomerById(UUID id) {

        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            LOGGER.error("customer with id {} not found!", id);
                            return new CustomerNotFoundException(
                                    "Customer not found!");
                        }
                );
        return mapper.customerToCustomerResponse(customer);
    }

    @Override
    public String removeCustomer(UUID id) {

        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            LOGGER.error("customer that you're trying to delete ID : {} does not exist!", id);
                            return new CustomerNotFoundException(
                                    "Customer not found!");
                        }
                );

        customerRepository.delete(customer);

        return String.format(
                "Customer %s removed successfully",
                customer.getFirstName() + customer.getLastName()
        );
    }

    @Override
    public Long countCustomers() {
        return customerRepository.count();
    }
}
