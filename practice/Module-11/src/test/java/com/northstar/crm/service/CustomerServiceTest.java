package com.northstar.crm.service;

import com.northstar.crm.entity.Customer;
import com.northstar.crm.exception.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.northstar.crm.entity.CustomerStatus.ACTIVE;
import static com.northstar.crm.entity.CustomerStatus.PROSPECT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Slides 6-11 - generated unit tests, then one test per row of the edge-case table.
 *
 * Naming is scenario_condition_result (slide 7): a failure report should tell you what broke
 * without opening the file.
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerNotifier notifier;

    private CustomerService service;

    // Slide 15 - "fresh mocks and isolated test setup for each test". JUnit builds a new instance
    // of this class per test method, so nothing a test does can leak into the next one.
    @BeforeEach
    void setUp() {
        service = new CustomerService(notifier);
        service.addCustomer(new Customer("CUS-1001", "Amina Khan", ACTIVE));
        service.addCustomer(new Customer("CUS-1002", "Ravi Patel", PROSPECT));
    }

    // Slide 8, Exercise 1 - the Arrange-Act-Assert skeleton, labelled once so the shape is obvious.
    @Test
    void activate_prospectRavi_setsStatusActive() {
        // Arrange - the fixture above already has Ravi as PROSPECT
        String id = "CUS-1002";

        // Act - exactly one call; anything else and a failure won't say which line caused it
        Customer result = service.activate(id, ACTIVE);

        // Assert - a domain value, not "not null"
        assertEquals(ACTIVE, result.getStatus());
    }

    // Slide 11 - null input asserts on the exception TYPE, not just that something was thrown.
    @Test
    void activate_nullId_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> service.activate(null, ACTIVE));
    }

    @Test
    void activate_blankId_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> service.activate("", ACTIVE));
    }

    // Slide 10, row "Null status".
    @Test
    void activate_nullStatus_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> service.activate("CUS-1001", null));
    }

    // Slide 11 - an unknown id is a different failure than a malformed one, so it gets its own type
    // and its own test. Collapsing the two would let a real bug pass.
    @Test
    void activate_unknownId_throwsNotFound() {
        assertThrows(CustomerNotFoundException.class, () -> service.activate("CUS-9999", ACTIVE));
    }

    // Slide 10, row "Duplicate customer".
    @Test
    void addCustomer_duplicateId_isRejected() {
        Customer sameId = new Customer("CUS-1001", "Impostor", PROSPECT);
        assertThrows(IllegalStateException.class, () -> service.addCustomer(sameId));
    }
}
