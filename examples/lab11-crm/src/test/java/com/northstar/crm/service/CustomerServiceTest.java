package com.northstar.crm.service;

import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {
    private CustomerService service;

    @BeforeEach
    void setUp() {
        service = new CustomerService();
    }

    @Test
    void addCustomerStoresNewCustomer() {
        Customer customer = new Customer(
                "CUS-1001",
                "Amina Khan",
                "amina.khan@example.com",
                "555-0101",
                CustomerStatus.ACTIVE,
                LocalDateTime.of(2024, 1, 15, 9, 30)
        );

        Customer saved = service.addCustomer(customer);

        assertEquals(customer, saved);
        assertTrue(service.findByCustomerId("CUS-1001").isPresent());
        assertEquals("CUS-1001", service.findByCustomerId("CUS-1001").get().getCustomerId());
    }

    @Test
    void addCustomerRejectsDuplicateId() {
        Customer first = new Customer(
                "CUS-1001",
                "Amina Khan",
                "amina.khan@example.com",
                "555-0101",
                CustomerStatus.ACTIVE,
                LocalDateTime.of(2024, 1, 15, 9, 30)
        );
        Customer duplicate = new Customer(
                "CUS-1001",
                "Someone Else",
                "other@example.com",
                "555-0000",
                CustomerStatus.PROSPECT,
                LocalDateTime.of(2024, 1, 16, 10, 0)
        );

        service.addCustomer(first);

        assertThrows(IllegalStateException.class, () -> service.addCustomer(duplicate));
    }

    @Test
    void updateStatusChangesExistingCustomer() {
        Customer customer = new Customer(
                "CUS-1002",
                "Sam Lee",
                "sam.lee@example.com",
                "555-0102",
                CustomerStatus.PROSPECT,
                LocalDateTime.of(2024, 2, 20, 8, 45)
        );
        service.addCustomer(customer);

        Customer updated = service.updateStatus("CUS-1002", CustomerStatus.ACTIVE);

        assertEquals(CustomerStatus.ACTIVE, updated.getStatus());
        assertEquals(CustomerStatus.ACTIVE, service.findByCustomerId("CUS-1002").get().getStatus());
    }

    @Test
    void updateStatusThrowsForUnknownCustomer() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateStatus("CUS-9999", CustomerStatus.ACTIVE));
    }

    @Test
    void findByStatusReturnsOnlyMatchingCustomers() {
        Customer prospect = new Customer(
                "CUS-1001",
                "Amina Khan",
                "amina.khan@example.com",
                "555-0101",
                CustomerStatus.PROSPECT,
                LocalDateTime.of(2024, 1, 15, 9, 30)
        );
        Customer active = new Customer(
                "CUS-1002",
                "Sam Lee",
                "sam.lee@example.com",
                "555-0102",
                CustomerStatus.ACTIVE,
                LocalDateTime.of(2024, 2, 20, 8, 45)
        );
        service.addCustomer(prospect);
        service.addCustomer(active);

        List<Customer> prospects = service.findByStatus(CustomerStatus.PROSPECT);

        assertEquals(1, prospects.size());
        assertEquals("CUS-1001", prospects.get(0).getCustomerId());
    }
}
