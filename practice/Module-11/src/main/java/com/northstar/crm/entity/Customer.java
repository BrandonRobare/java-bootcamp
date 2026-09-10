package com.northstar.crm.entity;

import java.util.Objects;

/** One customer row. Trimmed from Lab 10's POJO to just what Module 11's examples touch. */
public class Customer {

    private final String customerId;
    private final String fullName;
    private CustomerStatus status;

    public Customer(String customerId, String fullName, CustomerStatus status) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    // Identity is the business key, not the object address - two loads of CUS-1001 are the same customer.
    @Override
    public boolean equals(Object o) {
        return o instanceof Customer other && Objects.equals(customerId, other.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }

    @Override
    public String toString() {
        return "Customer{" + customerId + ", " + fullName + ", " + status + "}";
    }
}
