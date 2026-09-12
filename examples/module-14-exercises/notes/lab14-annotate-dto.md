# Lab 14 - Annotate Paper DTO

## Reference

Follows the Lab 14 starter `CustomerRequestDTO`, where the client supplies all four fields (the M13 `CustomerRequest` schema has all four as required).

| Field | Constraint / annotation |
| --- | --- |
| fullName | `@NotBlank` `@Size(min = 2, max = 100)` |
| email | `@NotBlank` `@Email` `@Size(max = 254)` |
| status | `@NotBlank`; must be a `CustomerStatus` name (ACTIVE, PROSPECT). Whether a new customer may start ACTIVE is a Lab 15 rule, not a constraint |
| customerId | `@NotBlank` `@Size(max = 32)`; optionally `@Pattern(regexp = "CUS-\\d{4}")` to match `CUS-1001` / `CUS-1002`. Server-assigned would drop it from the request entirely; the lab keeps it client-supplied |

## Step 2 - Paper annotations

List annotation names you would use: `@NotBlank`, `@Size`, `@Email`, `@Pattern`. All from `jakarta.validation.constraints`, not `javax`.

`@NotNull` vs `@NotBlank` for strings: `@NotNull` only rejects `null`, so `""` and `"   "` pass. `@NotBlank` rejects `null`, empty, and whitespace-only, which closes that gap. Use `@NotBlank` on every required string.

`@Email` on an optional field: it treats `null` and `""` as valid (most Jakarta constraints skip null and `@Email` accepts empty by design). So `@Email` alone does not make a field required; pair it with `@NotBlank` when it is.

## Step 3 - No Spring yet

Spring `@Valid` in this pre-lab? No. No controller, no `@Valid`, no `@RequestBody`. Lab 14 triggers validation by hand: `Validation.buildDefaultValidatorFactory().getValidator().validate(dto)` returns a `Set<ConstraintViolation<CustomerRequestDTO>>`; empty set means valid. Spring wires the same thing behind `@Valid` in Lab 24.

## Step 4 - Correlation

Where does `lab-request-001` live? In the `X-Correlation-Id` request header and in log lines and error messages. Never as a field on `CustomerRequestDTO`; it describes the request, not the customer. The facade takes it as a separate method parameter (`create(request, correlationId)`).

## Scope

Pre-lab only.
