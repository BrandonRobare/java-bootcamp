package com.northstar.crm.service;

import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerService {
    private final List<Customer> customers = new ArrayList<>();

    public Customer addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("customer must not be null");
        }
        if (customer.getCustomerId() == null || customer.getCustomerId().isBlank()) {
            throw new IllegalArgumentException("customerId must not be blank");
        }
        if (findByCustomerId(customer.getCustomerId()).isPresent()) {
            throw new IllegalStateException("Customer already exists: " + customer.getCustomerId());
        }
        customers.add(customer);
        return customer;
    }

    public Optional<Customer> findByCustomerId(String customerId) {
        if (customerId == null) return Optional.empty();
        return customers.stream()
                .filter(c -> customerId.equals(c.getCustomerId()))
                .findFirst();
    }

    public Customer updateStatus(String customerId, CustomerStatus status) {
        Customer customer = findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("No such customer: " + customerId));
        customer.setStatus(status);
        return customer;
    }

    // Failure experiment #2: implemented by-hand without Copilot
    public boolean deleteCustomer(String customerId) {
        if (customerId == null) return false;
        return customers.removeIf(c -> customerId.equals(c.getCustomerId()));
    }
}
