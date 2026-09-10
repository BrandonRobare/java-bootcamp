package com.northstar.crm.service;

import com.northstar.crm.entity.Customer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.northstar.crm.entity.CustomerStatus.ACTIVE;
import static com.northstar.crm.entity.CustomerStatus.PROSPECT;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Slide 34, Exercise 3 - "Trivial vs Real Asserts", and knowledge-check Q4:
 * the AI-generated test that creates false confidence is the one asserting only result != null.
 *
 * The reject rule: any AI test whose assertions never mention a domain value or outcome.
 */
class TrivialVsRealAssertTest {

    private final CustomerService service = new CustomerService(Mockito.mock(CustomerNotifier.class));

    /*
     * REJECT - this is what Copilot hands you first, and it can never fail:
     *
     *     @Test
     *     void testActivate() {
     *         Customer customer = service.activate("CUS-1002", ACTIVE);
     *         assertNotNull(customer);      // activate() cannot return null - always true
     *         assertTrue(true);             // true is true
     *     }
     *
     * Break activate() so it sets SUSPENDED instead of ACTIVE and that test still passes.
     * A test that stays green while production is broken is worse than no test: it is a
     * green build telling you a lie.
     */

    // ACCEPT - names the customer and the status, so it goes red the moment either one drifts.
    @Test
    void activate_amina_hasActiveStatus() {
        service.addCustomer(new Customer("CUS-1001", "Amina Khan", PROSPECT));

        service.activate("CUS-1001", ACTIVE);

        assertEquals(ACTIVE, service.findByCustomerId("CUS-1001").orElseThrow().getStatus());
    }

    @Test
    void untouchedRavi_staysProspect() {
        service.addCustomer(new Customer("CUS-1002", "Ravi Patel", PROSPECT));

        assertEquals(PROSPECT, service.findByCustomerId("CUS-1002").orElseThrow().getStatus());
    }
}
