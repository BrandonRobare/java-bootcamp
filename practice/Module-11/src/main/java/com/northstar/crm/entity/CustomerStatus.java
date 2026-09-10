package com.northstar.crm.entity;

/**
 * Slide 29 - "Domain Types Over Primitives".
 *
 * A String status accepts "ACTIVE", "active", "ACTIVEE" - the compiler catches none of them.
 * An enum lists the whole legal set in one place and makes the illegal ones unwritable.
 */
public enum CustomerStatus {
    PROSPECT,
    ACTIVE,
    SUSPENDED,
    CLOSED
}
