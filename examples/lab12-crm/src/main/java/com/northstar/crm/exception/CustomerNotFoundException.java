package com.northstar.crm.exception;

public class CustomerNotFoundException extends IllegalArgumentException {

    public CustomerNotFoundException(String customerId) {
        super("Customer not found: " + customerId);
    }

    public CustomerNotFoundException(String customerId, String correlationId) {
        super("Customer not found: " + customerId + " correlationId=" + correlationId);
    }
}
