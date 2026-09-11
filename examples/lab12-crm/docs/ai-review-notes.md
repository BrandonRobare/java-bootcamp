# AI review notes: Lab 12

Copilot was not used. The JCEF panel in IntelliJ is blank on this machine, so Copilot chat
does not render. Manual review substitute below.

## lab12-001

**Change under review:** storage type for the refactored service.

**Options:** keep `List<Customer>` with a stream `filter(equals)` lookup, as in Lab 11, or
switch to `Map<String, Customer>` keyed by `customerId`.

**Verdict:** accept `Map`. The two operations the service needs, "is this id taken" and "give
me this id", are `containsKey` and `get`, and both use `equals` by construction, so smell 5
cannot come back through a rewrite of a loop. The `List` version fixes the `==` bug too, but
only as long as nobody edits the lambda.

**Risk caught:** with a `Map` there is no iteration order, so a future `listAll` would need a
`LinkedHashMap` or a sort. Not needed now; noted.

## lab12-002

**Change under review:** how the correlation id reaches the service.

**Options:** a parameter on every public method, MDC (my pre-lab answer), or a field set by
the caller.

**Verdict:** accept-with-edits: field plus `setCorrelationId`. MDC needs slf4j and there is
no logging dependency in the pom; adding one for a single id is more than the lab asks. The
starter test already calls `setCorrelationId`, so the field matches the contract that was
handed to me. A parameter on every method would leak into the Lab 13 controller signatures.

**Risk caught:** the field is per-service-instance, not per-request. Two callers sharing one
`CustomerService` would overwrite each other's id. Fine for `Main` and tests; wrong for a
server. Swap for MDC when the controller arrives.
