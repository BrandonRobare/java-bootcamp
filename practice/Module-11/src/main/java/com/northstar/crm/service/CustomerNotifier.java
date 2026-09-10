package com.northstar.crm.service;

/**
 * Slide 25 - Exercise 2, "Notifier Extract Plan".
 *
 * The smell this replaces:
 *     System.out.println("Activated " + id);            // testability smell
 *     emailClient.send(customer.getEmail(), ...);       // hidden side effect
 *
 * Buried I/O cannot be observed by a test - you can't assert on an email that already left.
 * Pull it behind an interface and the test can hand the service a mock instead.
 */
public interface CustomerNotifier {
    void notifyActivated(String customerId);
}
