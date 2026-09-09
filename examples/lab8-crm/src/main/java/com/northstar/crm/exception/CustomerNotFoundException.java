package com.northstar.crm.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String customerId) {
        // DONE: call super("Customer not found: " + customerId)
        super("Customer not found: " + customerId);
    }
}
