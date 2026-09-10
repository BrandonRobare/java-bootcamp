package com.northstar.crm.exception;

/**
 * Slide 10, row "Missing customer" - an unknown id is its own failure mode, not a generic
 * IllegalArgumentException. A typed exception is what a test can assert on precisely.
 */
public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String customerId) {
        super("Customer not found: " + customerId);
    }
}
