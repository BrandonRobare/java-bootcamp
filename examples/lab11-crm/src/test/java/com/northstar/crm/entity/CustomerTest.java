package com.northstar.crm.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    @Test
    void equalsIsBasedOnCustomerIdOnly() {
        Customer customer1 = new Customer(
                "CUS-1001",
                "Ravi Patel",
                "ravi.patel@example.com",
                "555-0101",
                CustomerStatus.ACTIVE,
                LocalDateTime.of(2024, 1, 15, 9, 30)
        );
        Customer customer2 = new Customer(
                "CUS-1001",
                "Sonia Patel",
                "sonia.patel@example.com",
                "555-0102",
                CustomerStatus.PROSPECT,
                LocalDateTime.of(2024, 2, 15, 9, 30)
        );
        Customer customer3 = new Customer(
                "CUS-1002",
                "Ravi Patel",
                "ravi.patel@example.com",
                "555-0101",
                CustomerStatus.ACTIVE,
                LocalDateTime.of(2024, 1, 15, 9, 30)
        );

        assertEquals(customer1, customer2);
        assertTrue(!customer1.equals(customer3));
    }

    @Test
    void toStringIncludesCustomerId() {
        Customer customer = new Customer(
                "CUS-1002",
                "Ravi Patel",
                "ravi.patel@example.com",
                "555-0102",
                CustomerStatus.ACTIVE,
                LocalDateTime.of(2024, 3, 1, 10, 15)
        );

        assertTrue(customer.toString().contains("CUS-1002"));
    }
}
