package com.northstar.crm.service;

import com.northstar.crm.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.northstar.crm.entity.CustomerStatus.ACTIVE;
import static com.northstar.crm.entity.CustomerStatus.PROSPECT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Slides 14-16 - mocks, and the verify() half that proves a call actually happened.
 *
 * The rule from slide 15: mock the EXTERNAL dependency (the notifier), never the class under test.
 * Customer stays a real object - mocking a value class buys nothing and hides real behavior.
 */
@ExtendWith(MockitoExtension.class)
class CustomerNotifierMockTest {

    @Mock
    private CustomerNotifier notifier;

    private CustomerService service;

    @BeforeEach
    void setUp() {
        service = new CustomerService(notifier);
        service.addCustomer(new Customer("CUS-1002", "Ravi Patel", PROSPECT));
    }

    // assertEquals proves the state changed. verify() proves the side effect fired.
    // Without the verify, deleting the notifier call entirely would leave this test green.
    @Test
    void activate_prospect_notifiesExactlyOnce() {
        service.activate("CUS-1002", ACTIVE);
        verify(notifier, times(1)).notifyActivated("CUS-1002");
    }

    // Slide 10, row "Same status" -> "No unnecessary notification".
    // never() is the assertion that catches a duplicate email, which no state check ever would.
    @Test
    void activate_statusAlreadySet_sendsNoNotification() {
        service.activate("CUS-1002", ACTIVE);
        service.activate("CUS-1002", ACTIVE);
        verify(notifier, times(1)).notifyActivated("CUS-1002");
    }

    @Test
    void activate_toSameStatusItAlreadyHad_neverNotifies() {
        service.activate("CUS-1002", PROSPECT);
        verify(notifier, never()).notifyActivated("CUS-1002");
    }

    /**
     * Slide 16, copied straight off the slide.
     *
     * doThrow(...).when(mock) is how you stub a void method - when(mock.foo()) can't be used
     * because a void call has no return value to hand to when().
     *
     * This is the test that documents the recovery decision: SMTP is down, the status change
     * still stands. You cannot make a real mail server fail on demand; a mock does it in one line.
     */
    @Test
    void activate_notifierFails_stillUpdatesStatus() {
        String id = "CUS-1002";
        doThrow(new RuntimeException("SMTP down")).when(notifier).notifyActivated(id);

        Customer result = service.activate(id, ACTIVE);

        assertEquals(ACTIVE, result.getStatus());
        verify(notifier, times(1)).notifyActivated(id);
    }
}
