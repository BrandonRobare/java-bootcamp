# Lab 11 acceptance checklist TODOs

Imagine Copilot just generated a CustomerServiceTest. Score it below.

AAA structure present? yes — arrange/act/assert are separated, though two methods
put the act line inside the assert call.
Asserts use Amina/Ravi values (CUS-1001 ACTIVE / CUS-1002 PROSPECT)? partly — Amina
is checked against ACTIVE, Ravi's test only asserts non-null.
Trivial assertNotNull-only / assertTrue(true) rejected? yes — two assertNotNull-only
methods flagged, one assertTrue(true) deleted outright.
Correlation mention lab-request-001 (notes/comments OK)? yes — added as a comment on
the fixture setup, absent from the generated code.
Coverage gap noted for Labs 17–18? yes — see gap sentence below.
Accept / Reject / Edit: Edit

## Gap sentence (Labs 17–18)
Deep Mockito verify/interaction checking waits for Lab 18; JaCoCo coverage reporting
and parameterized-test depth wait for Lab 17, so today's suite proves behavior on two
fixtures only and is not a coverage claim.

## One reason for Accept / Reject / Edit
Edit: the ACTIVE assert on Amina is worth keeping, but Ravi's assertNotNull-only
method has to become assertEquals(PROSPECT, ...) before any of it goes in.
