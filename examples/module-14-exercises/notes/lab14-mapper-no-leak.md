# Lab 14 - Mapper No-Leak Rule

## Step 1 - toDto

CUS-1001 response fields: customerId, fullName, email, status, createdAt.

`toResponse(Customer)` is a whitelist: it copies those five fields into `CustomerResponseDTO.of(...)` and nothing else. `createdAt` is server-set and read-only, so it is safe to show but is never accepted on the request side. `phone` stays on the entity and does not cross.

`toEntity(CustomerRequestDTO)` goes the other way: customerId, fullName, email, and `CustomerStatus.valueOf(status)`. The server sets `createdAt` to now; the client cannot supply it.

## Step 2 - Forbidden

1. Persistence-only columns: `phone` today; `version`, `createdBy`, `updatedBy`, soft-delete flags later.
2. Anything secret or internal: password hashes, internal risk scores, raw database ids if they differ from the public `CUS-` id, lazy JPA relations that would serialize a whole related table.

Rule: the mapper never copies "every field by habit." Each field on the wire is there because the M13 contract named it.

## Step 3 - Activate DTO

Body fields: customerId only. Correlation: `X-Correlation-Id: lab-request-001` as a header, outside the body.

Debug: `toEntity` copying a client-supplied `status=ACTIVE` on create is not a leak (nothing internal escapes). It is a business-rule problem: whether a new customer may start ACTIVE, or must be PROSPECT first, is a transition rule. Lab 15 owns that. Lab 14 only checks the value is one of the enum's names.

Predict: mapping a `null` email to `""` does not hide the validation failure if validation runs on the DTO before the mapper, which is the facade's order (validate, then map). `@NotBlank` rejects both `null` and `""`. It would hide it only if the mapper ran first and the entity were validated instead, which is exactly why validation lives on the request DTO.

## Step 4 - Prep boundary

Lab that owns deep transitions: Lab 15. DTOs before deep service rules; Lab 14 proves the shape and the validation, Lab 15 decides what state changes are allowed.

Manual mapper only. No MapStruct in this lab.

## Scope

Pre-lab only.
