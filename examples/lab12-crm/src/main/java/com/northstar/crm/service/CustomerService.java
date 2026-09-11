package com.northstar.crm.service;

import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import com.northstar.crm.exception.CustomerNotFoundException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    private static final Logger LOG = System.getLogger(CustomerService.class.getName());

    private final Map<String, Customer> customersById = new HashMap<>();
    private String correlationId = "none";

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Customer createCustomer(String customerId, String fullName, String email,
                                   String phone, CustomerStatus status) {
        requireNonBlank(customerId, "customerId");
        requireNonBlank(fullName, "fullName");
        requireUniqueId(customerId);

        Customer customer = new Customer(customerId, fullName, email, phone,
                status == null ? CustomerStatus.PROSPECT : status, LocalDateTime.now());
        customersById.put(customerId, customer);
        log(Level.INFO, "createCustomer ok customerId=" + customerId);
        return customer;
    }

    public Customer getCustomer(String customerId) {
        return requireExisting(customerId);
    }

    public Customer updateStatus(String customerId, CustomerStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("newStatus is required" + suffix());
        }
        Customer customer = requireExisting(customerId);
        CustomerStatus oldStatus = customer.getStatus();
        customer.setStatus(newStatus);
        log(Level.INFO, "updateStatus ok customerId=" + customerId
                + " from=" + oldStatus + " to=" + newStatus);
        return customer;
    }

    private void requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required" + suffix());
        }
    }

    private void requireUniqueId(String customerId) {
        if (customersById.containsKey(customerId)) {
            log(Level.WARNING, "createCustomer duplicate customerId=" + customerId);
            throw new IllegalStateException("Duplicate customerId: " + customerId + suffix());
        }
    }

    private Customer requireExisting(String customerId) {
        Customer found = customersById.get(customerId);
        if (found == null) {
            log(Level.WARNING, "getCustomer miss customerId=" + customerId);
            throw new CustomerNotFoundException(customerId, correlationId);
        }
        return found;
    }

    private String suffix() {
        return " correlationId=" + correlationId;
    }

    private void log(Level level, String message) {
        LOG.log(level, message + suffix());
    }
}
