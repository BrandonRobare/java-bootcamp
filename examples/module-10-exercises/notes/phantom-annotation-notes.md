# Phantom annotation hunt

| Snippet finding | Phantom? | Action (Reject / Edit) |
| --------------- | -------- | ---------------------- |
| @MyPhantomAnnotation in suggestion | Yes | Reject — remove and request plain Java alternative |
| jakarta.persistence.* import | Yes (if present without JPA) | Reject — remove and restate constraints |

## Reference table

| Seen in suggestion | Likely real? | Prep action |
| --- | --- | --- |
| @Entity / @Table | JPA only | Defer — not Lab 10 scope |
| @Service / @Autowired | Spring | Defer — hosting labs later |
| @NotNull (Jakarta) | Validation lib | Name it; don't invent imports |
| public record Customer(...) | Java 16+ | OK on JDK 21 |
| @MyPhantom (trap) | Phantom (invented) | Reject — do not accept invented annotations |

## Reject rule

Reject any import I cannot name from JDK 21 or an agreed Maven dependency. If unsure, ask: "Which library provides this annotation?" and require a named, verifiable dependency.

## Fixture check (Ravi)

If any suggestion hard-codes `CUS-1002` (Ravi) as ACTIVE, mark as FAIL — Ravi should be PROSPECT in fixtures.

## Out of scope

REST hosting (Labs 13/24) and Spring Boot are out of scope for this pre-lab. Reject Copilot SOAP/WSDL suggestions — this course uses REST/OpenAPI.

## Debug / design challenge

If a snippet shows `@Column` or `jakarta.persistence` imports, remove them and restate: "Plain Java fields only; no JPA/Spring annotations or imports."
