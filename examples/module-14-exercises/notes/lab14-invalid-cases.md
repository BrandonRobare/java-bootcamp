# Lab 14 - Invalid Cases Catalog

## Step 1 - Create invalids

Each of these is a `CustomerRequestDTO` that `validator.validate(dto)` rejects before `CustomerService` ever sees it.

| # | Payload | Violation | Annotation that catches it |
| --- | --- | --- | --- |
| 1 | fullName `""` or `"   "`, rest Ravi-shaped | fullName must not be blank | `@NotBlank` |
| 2 | email `"ravi.singh"` (no `@`) | email must be a well-formed address | `@Email` |
| 3 | email `null` | email must not be blank | `@NotBlank` (`@Email` alone would let null through) |
| 4 | status `"ACTIVATED"` | not a `CustomerStatus` name | `@NotBlank` passes; `CustomerStatus.valueOf` in the mapper throws `IllegalArgumentException`. A `@Pattern(regexp = "ACTIVE\|PROSPECT")` would move it into validation |
| 5 | fullName `"A"` | size must be between 2 and 100 | `@Size(min = 2, max = 100)` |
| 6 | customerId longer than 32 chars | size must be at most 32 | `@Size(max = 32)` |

Predict: if an invalid email still reaches `CustomerService`, the facade skipped `validateOrThrow` before mapping. The order is validate, then map, then service.

## Step 2 - Activate invalids

- Activate with `customerId` null or blank: Bean Validation failure on the request, `@NotBlank`.
- Activate `CUS-9999`: passes validation (it is a well-formed id) and fails in the service with `CustomerNotFoundException`. Not-found is a service/lookup failure, not a constraint violation. The error message carries `lab-request-001`.

Debug: `CUS-9999` is a service layer failure. Bean Validation checks shape; it cannot know which ids exist.

## Step 3 - Valid control

Create Ravi-shaped: customerId `CUS-1002`, fullName `Ravi Singh`, email `ravi.singh@example.com`, status `PROSPECT`. Expect zero violations, then a `CustomerResponseDTO` with those four fields plus a server-set `createdAt`.

Amina (`CUS-1001`, ACTIVE) stays the valid lookup fixture. `CUS-9999` is the only unknown id.

## Scope

Pre-lab only.
