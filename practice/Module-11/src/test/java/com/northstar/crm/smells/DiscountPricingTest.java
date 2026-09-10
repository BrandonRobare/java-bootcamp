package com.northstar.crm.smells;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Slide 18, step 4 - "Test: run tests, verify behavior".
 *
 * These assertions are the proof that the slide-23 refactor was a refactor and not a rewrite:
 * they were written against the smelly version and pass unchanged against the clean one.
 * Constants named STANDARD_DISCOUNT and VIP_DISCOUNT still multiply to the same money.
 */
class DiscountPricingTest {

    // Doubles never compare exactly - the delta is the tolerance, not decoration.
    private static final double CENT = 0.001;

    @Test
    void standardTier_gets10PercentOff() {
        assertEquals(90.0, DiscountPricing.priceFor("STANDARD", 100.0), CENT);
    }

    @Test
    void vipTier_gets20PercentOff() {
        assertEquals(80.0, DiscountPricing.priceFor("VIP", 100.0), CENT);
    }

    // The before-version used tier.equals("VIP") and would have thrown NullPointerException here.
    // Behavior for valid input is unchanged; invalid input just stopped crashing - slide 20's rule.
    @Test
    void nullTier_fallsBackToStandard() {
        assertEquals(90.0, DiscountPricing.priceFor(null, 100.0), CENT);
    }

    @Test
    void compoundInterest_matchesTheManualCalculation() {
        // 1000 at 5% for 2 years -> 1102.50, so 102.50 of interest.
        assertEquals(102.50, DiscountPricing.computeCompoundInterest(1000.0, 5.0, 2), CENT);
    }
}
