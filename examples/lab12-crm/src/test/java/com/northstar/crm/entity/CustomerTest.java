package com.northstar.crm.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    @Test
    void equalsIsBasedOnCustomerIdOnly() {
        Customer c1 = new Customer();
        c1.setCustomerId("CUS-1001");
        c1.setFullName("Amina Khan");
        c1.setStatus(CustomerStatus.ACTIVE);

        Customer c2 = new Customer();
        c2.setCustomerId("CUS-1001");
        c2.setFullName("Different Name");
        c2.setStatus(CustomerStatus.SUSPENDED);

        assertEquals(c1, c2);
    }

    @Test
    void toStringIncludesCustomerId() {
        Customer c = new Customer();
        c.setCustomerId("CUS-1002");
        assertTrue(c.toString().contains("CUS-1002"));
    }
}
