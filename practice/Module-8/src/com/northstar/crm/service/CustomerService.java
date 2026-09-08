package com.northstar.crm.service;

import com.northstar.crm.dto.CustomerRequest;
import com.northstar.crm.dto.CustomerResponse;
import com.northstar.crm.entity.Customer;
import com.northstar.crm.repository.CustomerRepository;
import java.util.NoSuchElementException;

// SERVICE = the business rules. This is the only layer allowed to decide things.
// It also does the translating: DTO in, entity down, entity back, DTO out.
//
// It holds a CustomerRepository (the interface), so it has no idea whether the
// data lives in a HashMap, PostgreSQL, or a file. That is on purpose.
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public CustomerResponse createCustomer(CustomerRequest request) {

        // Business rule. It lives HERE, not in the controller, not in the entity.
        // If the rule changes, there is exactly one file to open.
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }

        // Step 4a: map the request DTO into an entity (DTO shape -> database shape)
        Customer customer = new Customer(request.getName(), request.getEmail());

        // Another business decision: new customers start ACTIVE.
        customer.setStatus("ACTIVE");

        // Step 4b-6: hand the entity down to the repository, get the saved one back
        Customer saved = repository.save(customer);

        // Step 7: map the entity into a response DTO (database shape -> client shape)
        return toResponse(saved);
    }

    public CustomerResponse getCustomer(Long id) {
        Customer found = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No customer with id " + id));
        return toResponse(found);
    }

    // One place that knows how an entity becomes a response. Change it once, change it everywhere.
    private CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName(), customer.getStatus());
    }
}
