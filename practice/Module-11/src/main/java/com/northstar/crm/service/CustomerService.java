package com.northstar.crm.service;

import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import com.northstar.crm.exception.CustomerNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The class under test for Lab 11, already carrying the refactors the slides ask for.
 *
 * Slide 20  - validate first, then do the work.
 * Slide 25  - the notifier is a constructor-injected collaborator, not inline I/O.
 * Slide 43  - validateCustomerId() extracted so three methods stop repeating the same two checks.
 */
public class CustomerService {

    private final List<Customer> customers = new ArrayList<>();
    private final CustomerNotifier notifier;

    // Constructor injection is the whole reason this class is testable: the test passes a mock,
    // production passes the real emailing implementation. Neither knows about the other.
    public CustomerService(CustomerNotifier notifier) {
        this.notifier = notifier;
    }

    public Customer addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("customer must not be null");
        }
        validateCustomerId(customer.getCustomerId());
        // Slide 10, row "Duplicate customer" - reject, don't silently keep two rows with one id.
        if (findByCustomerId(customer.getCustomerId()).isPresent()) {
            throw new IllegalStateException("Customer already exists: " + customer.getCustomerId());
        }
        customers.add(customer);
        return customer;
    }

    public Optional<Customer> findByCustomerId(String customerId) {
        return customers.stream()
                .filter(c -> c.getCustomerId().equals(customerId))
                .findFirst();
    }

    /**
     * Slide 20's before/after, in full.
     *
     * Before: return find(id).setStatus(s);  -- trusts the caller, dies deep inside with an NPE
     *                                           whose stack trace names a line the caller never wrote.
     * After:  validate, then the same one line of real work. The happy path is byte-for-byte
     *         identical; only bad input behaves better. That is what "refactor" means.
     */
    public Customer activate(String customerId, CustomerStatus status) {
        validateCustomerId(customerId);
        if (status == null) {
            throw new IllegalArgumentException("status must not be null");
        }

        Customer customer = findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        // Slide 10, row "Same status" - repeating a status is not a state change, so nobody gets emailed.
        if (customer.getStatus() == status) {
            return customer;
        }
        customer.setStatus(status);

        // Slide 16 - the notifier is best-effort. A dead SMTP server must not undo a status change
        // that already succeeded. Recovery is a decision the slides force you to make out loud.
        try {
            notifier.notifyActivated(customerId);
        } catch (RuntimeException e) {
            System.out.println("[warn] notify failed for " + customerId + ": " + e.getMessage());
        }
        return customer;
    }

    /**
     * Slide 43, Task 4 - "Refactor Duplication: extract validateCustomerId(); confirm with tests".
     *
     * Two callers had the same null/blank pair inline. One copy means one place to fix it, and
     * every caller gets the same message - which is what the tests then assert on.
     */
    private void validateCustomerId(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("customerId must not be null or blank");
        }
    }
}
