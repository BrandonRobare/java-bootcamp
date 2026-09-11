# Lab 13 - Sketch the OpenAPI Skeleton

## Top-Level Keys

`openapi: 3.0.3` (spec version), `info` (title `Northstar CRM Customers API`, version `1.0.0`, description), `servers` (local Spring Boot at `http://localhost:8080`), `paths` (the two customer paths), and `components` (shared `CorrelationId` parameter, shared `Error` response, and the `Customer`, `CustomerRequest`, `CustomerPage`, and `ErrorResponse` schemas).

## Two Paths

`/api/v1/customers`:
- `GET`: list customers, paged (`page`, `size` max 100), filterable by `status` (enum `ACTIVE`/`PROSPECT`), sortable; returns 200 with `CustomerPage`, 400 on bad params (e.g. `size=500`).
- `POST`: create customer from a `CustomerRequest`; 201 with `Location` header and `Customer` body, 400 on validation failure, 409 when the `customerId` already exists (e.g. re-POSTing CUS-1001).

`/api/v1/customers/{customerId}` (path param is a string, CUS-1001, not an integer):
- `GET`: 200 with `Customer` (Amina Khan, CUS-1001, ACTIVE), 404 for CUS-9999.
- `PUT`: replace the whole customer; 200, or 404 if missing.
- `DELETE`: 204 no body, or 404 if missing.

Both paths take the shared `X-Correlation-Id` header (default `lab-request-001`) and every failure uses the shared `Error` response (`ErrorResponse` body, e.g. `Customer not found: CUS-9999`).

## Customer Schema

`Customer` is a `type: object` with required `[customerId, fullName, email, status]`:
- `customerId`: **string**, example `CUS-1001`. If it were typed integer, it fails in three places: the spec linter flags `example: CUS-1001` against `type: integer`; a generated Angular client types the field as `number`, so `CUS-1001` never compiles into a request; and a Spring controller with `@PathVariable Long customerId` throws `MethodArgumentTypeMismatchException` and returns 400 for every real request.
- `fullName`: string, example `Amina Khan`.
- `email`: string with `format: email`, example `amina.khan@example.com` (`ravi.singh@example.com` for CUS-1002).
- `status`: **string with `enum: [ACTIVE, PROSPECT]`** (Amina is ACTIVE, Ravi is PROSPECT). A free-text string would let `SOMETHING`, `active`, or `ACTIVE ` pass validation; the 400 `PATCH` status case only works because the enum rejects anything outside the two values.

`CustomerRequest` carries the same four properties for POST/PUT bodies today, but is a separate schema so it can diverge: server-generated fields (`createdAt`, links) get added to `Customer` without letting clients send them. `CustomerPage` wraps the collection response (`content` array of `Customer`, `page`, `size`, `totalElements`).

## API-First

Sketching the contract in `openapi/northstar-crm-customers.yaml` before any controller means the Angular team can review the types, responses, and headers and generate a mock server from it while Spring Boot is still being built. The backend then has to match the written document, so the team builds against a reviewable, versionable artifact instead of discovering the API in generated client code at integration time.

## Scope

Pre-lab only: do not finish the full lab in this exercise.
