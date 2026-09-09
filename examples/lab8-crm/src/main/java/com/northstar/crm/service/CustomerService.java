package com.northstar.crm.service;

import com.northstar.crm.dto.CustomerRequest;
import com.northstar.crm.dto.CustomerResponse;
import com.northstar.crm.repository.CustomerRepository;

/**
 * Business rules live here. Controllers must not bypass this layer.
 */
public class CustomerService {

    // DONE: keep a final CustomerRepository field + constructor injection (even if unused in Lab 8)
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public CustomerResponse create(CustomerRequest request) {
        // DONE: throw UnsupportedOperationException("Lab 8 stub — implement later")
        throw new UnsupportedOperationException("Lab 8 stub — implement later");
    }

    public CustomerResponse getById(String customerId) {
        // DONE: throw UnsupportedOperationException("Lab 8 stub — implement later")
        throw new UnsupportedOperationException("Lab 8 stub — implement later");
    }
}
