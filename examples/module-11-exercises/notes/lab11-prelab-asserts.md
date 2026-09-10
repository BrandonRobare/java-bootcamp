# Lab 11 pre-lab — trivial vs real asserts

## Trivial (reject these — false confidence)
1. assertNotNull(customer);
2. assertTrue(true);

Why weak: neither one reads a single field. The test goes green with the wrong
status, the wrong id, or an activate() that never ran — a non-null object only
proves the constructor returned.

## Meaningful (prefer these)
1. assertEquals(CustomerStatus.ACTIVE, amina.getStatus());    // CUS-1001 Amina Khan
2. assertEquals(CustomerStatus.PROSPECT, ravi.getStatus());   // CUS-1002 Ravi Singh

These name a domain value, so they fail the moment the status transition breaks.

## Review rule (one sentence)
Reject any AI-suggested test whose asserts never mention a domain value
(CUS-1001/CUS-1002, ACTIVE/PROSPECT) or an outcome — status, id, or thrown exception.

## Scope
Pre-lab only — do not finish Lab 11.
