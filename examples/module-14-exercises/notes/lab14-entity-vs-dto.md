# Lab 14 - Entity vs DTO

## Step 1 - Definitions

Entity = the persistence shape: what the system stores and works with internally, so it carries every field the business needs, including ones no client should see. Ours is `entity/Customer` (customerId, fullName, email, phone, status, createdAt) with `CustomerStatus`.

DTO = the API contract shape: only the fields the client and server agreed on, in the direction they travel. Ours are `CustomerRequestDTO` (inbound, validated) and `CustomerResponseDTO` (outbound). They match the M13 OpenAPI schemas `CustomerRequest` and `Customer`.

## Step 2 - Leak risks

1. Fields the API never promised go over the wire. `Customer` already has `phone` and `createdAt`; neither is in the M13 contract, and both ship to every client the moment the facade returns the entity instead of a DTO.
2. Future persistence columns become public automatically. Add `version`, `createdBy`, or a lazy JPA relation to the entity later and they appear in JSON without anyone deciding they should. If a client sees `version`/`createdBy` after a schema change, that is the proof the facade is returning `Customer`, not `CustomerResponseDTO`.

## Step 3 - Fixture DTO fields

Amina (`CUS-1001`) response fields: customerId, fullName, email, status. Values: `CUS-1001`, `Amina Khan`, `amina.khan@example.com`, `ACTIVE`. No `phone`, no `createdAt`, no persistence annotations on the DTO class.

## Scope

Pre-lab only: do not finish the full lab in this exercise.
