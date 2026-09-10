package com.northstar.crm.smells;

/**
 * Slides 22-23 - "Detecting Code Smells" and "Before and After".
 *
 * Before, exactly as the slide has it:
 *
 *     double price = 0.90 * base;
 *     if (tier.equals("VIP")) price = 0.80 * base;
 *
 * Three smells in two lines:
 *   Magic Number  - 0.90 and 0.80 are business rules with no name to grep for.
 *   Magic String  - "VIP" is a typo away from silently charging full price.
 *   Duplication   - the tier test will be copy-pasted the moment a third tier appears.
 *
 * After: named constants, the tier check behind one method, and a ternary that reads like the
 * rule it encodes. Same numbers out - the only thing that changed is whether a human can read it.
 */
public final class DiscountPricing {

    // A named constant is searchable and changeable in one place; 0.90 scattered across files is not.
    static final double STANDARD_DISCOUNT = 0.90;
    static final double VIP_DISCOUNT = 0.80;

    private DiscountPricing() {
    }

    public static double priceFor(String tier, double base) {
        return isVip(tier) ? base * VIP_DISCOUNT : base * STANDARD_DISCOUNT;
    }

    // Extract Method: one definition of "what counts as VIP". equalsIgnoreCase on the constant
    // side also survives a null tier, which the slide's tier.equals("VIP") would have thrown on.
    private static boolean isVip(String tier) {
        return "VIP".equalsIgnoreCase(tier);
    }

    /**
     * Slide 27 - "Refactoring Quality Checklist", the naming half.
     *
     * Before: double c(double p, double r, int y, int m) {...}
     *         Four one-letter parameters. You cannot call this correctly without reading its body.
     * After:  the name says what it computes and the parameters say what they are.
     */
    public static double computeCompoundInterest(double principal, double ratePercent, int years) {
        return principal * Math.pow(1 + ratePercent / 100, years) - principal;
    }
}
